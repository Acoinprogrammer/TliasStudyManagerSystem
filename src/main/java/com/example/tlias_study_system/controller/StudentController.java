package com.example.tlias_study_system.controller;

import com.example.tlias_study_system.pojo.Restful;
import com.example.tlias_study_system.service.Impl.StudentServiceImpl;
import com.example.tlias_study_system.util.Upload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@RestController
//@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:5173")
//5173
public class StudentController {
    @Autowired
    private StudentServiceImpl studentserviceimpl;
    @Autowired(required = false)
    private RabbitTemplate rabbittemplate;

    @Autowired(required = false)
    private StringRedisTemplate stringRedisTemplate;
    private static final ObjectMapper objectmapper = new ObjectMapper();

    public static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private static final String GET_ALL_STUDENT_KEY = "student:all";
    private static final String GET_ALL_STUDENT_LOCK = "student:all:lock";
    private static final long GET_ALL_STUDENT_KEY_TIME = 30;
    private static final long GET_ALL_STUDENT_LOCK_TIME = 5;
    private static final String GET_UNION_STUDENT_KEY = "student:union";
    private static final String GET_UNION_STUDENT_LOCK = "student:union:lock";
    private static final long GET_UNION_STUDENT_KEY_TIME = 5;
    private static final long GET_UNION_STUDENT_LOCK_TIME = 3;
    @GetMapping("/GetStudentCount")
    public Restful GetStudentCount(){return Restful.success(studentserviceimpl.GetStudentCount());}

    @GetMapping("/LoadingData")
    public Restful LoadingData() throws JsonProcessingException {
        List list = null;
        String loadingData = stringRedisTemplate.opsForValue().get(GET_ALL_STUDENT_KEY);
        if(loadingData!=null && !loadingData.isEmpty()){list = objectmapper.readValue(loadingData,List.class);}
        if(list!=null){
            log.info("获取GET_ALL_STUDENT_KEY成功");
            return Restful.success(list);
        }
        try{
            if(tryLock()){
                Thread.sleep(100);
                return LoadingData();
            }
        }catch(Exception e){log.error("服务器异常:{}",e.getMessage());}
        finally{unLock();}
        log.info("获取缓存失败,进行缓存重建");
        list = studentserviceimpl.LoadingData();
        stringRedisTemplate.opsForValue().set(GET_ALL_STUDENT_KEY,objectmapper.writeValueAsString(list),Duration.ofMinutes(GET_ALL_STUDENT_KEY_TIME));
        log.info("缓存重建成功");
        return Restful.success(list);
    }
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/DelStudent")
    public Restful DelStudent(@RequestParam("StuId") int StuId) {
        String HeadImage = studentserviceimpl.GetHeadImage(StuId);
        if(HeadImage==null || HeadImage.isEmpty() || studentserviceimpl.DelStudent(StuId)==0){return Restful.error("删除失败");}
        try{
            Upload.DropHeadImg(HeadImage);
            log.info("删除学生头像成功");
        }catch(Exception e){
            log.error("删除学生头像失败:{}",e.getMessage());
            return Restful.error("删除失败,请重新尝试");
        }
        stringRedisTemplate.delete(GET_ALL_STUDENT_KEY);
        stringRedisTemplate.delete(GET_UNION_STUDENT_KEY);
        return Restful.success();
    }
    @GetMapping("/UnionQueryStudent")
    public Restful UnionQueryStudent(@RequestParam int id, @RequestParam String name, @RequestParam String fromClass, @RequestParam String manager) throws JsonProcessingException {
        List list = null;
        String unionFindStudent = id+name+fromClass+manager;
        String cache = stringRedisTemplate.opsForValue().get(unionFindStudent);
        if(cache!=null && !cache.isEmpty()){list = objectmapper.readValue(cache,List.class);}
        if(list!=null){
            log.info("获取缓存{}成功",unionFindStudent);
            return Restful.success(list);
        }
        try{
            if(tryLockUnion()){
                Thread.sleep(200);
                return UnionQueryStudent(id,name,fromClass,manager);
            }
        }
        catch(Exception e){log.info("服务器异常:{}",e.getMessage());}
        finally{unLockUnion();}
        log.info("缓存{}获取失败,重构缓存中",unionFindStudent);
        list = studentserviceimpl.UnionQueryStudent(id,name,fromClass,manager);
        stringRedisTemplate.opsForValue().set(unionFindStudent,objectmapper.writeValueAsString(list),Duration.ofMinutes(GET_UNION_STUDENT_KEY_TIME));
        log.info("缓存{}获取成功",unionFindStudent);
        return Restful.success(list);
    }
    public boolean tryLock(){return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(GET_ALL_STUDENT_LOCK,"1", Duration.ofSeconds(GET_ALL_STUDENT_LOCK_TIME)));}
    public void unLock(){stringRedisTemplate.delete(GET_ALL_STUDENT_LOCK);}

    public boolean tryLockUnion(){return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(GET_UNION_STUDENT_LOCK,"1", Duration.ofSeconds(GET_UNION_STUDENT_LOCK_TIME)));}
    public void unLockUnion(){stringRedisTemplate.delete(GET_UNION_STUDENT_LOCK);}

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/AddStudent")
    public Restful AddStudent(
            @RequestParam("name") String name,
            @RequestParam("gender")String gender,
            @RequestParam("manager")String manager,
            @RequestParam("age")int age,
            @RequestParam("fromClass")String fromClass,
            @RequestParam("AddUploadFile")MultipartFile multipartFile){
        Integer managerId = studentserviceimpl.QueryExitedManager(manager);
        if(managerId==null){return Restful.warning("班主任不存在,请重新输入");}
        Integer classId = studentserviceimpl.QueryExitedClass(fromClass);
        if(classId==null){return Restful.warning("班级不存在,请重新输入");}
        String Addfilename = String.format("%s%s%s",name, UUID.randomUUID(),multipartFile.getOriginalFilename());
        if(studentserviceimpl.AddStudent(name,gender,manager,age,fromClass,Addfilename,managerId,classId)<=0){return Restful.error("添加失败");}
        try{
            Upload.UploadHeadImg(Addfilename,multipartFile);
            log.info("{}文件上传成功",Addfilename);
        }catch(Exception e){
            log.error("上传文件失败:{}",e.getMessage());
            return Restful.error("上传文件失败,请重新尝试");
        }
        stringRedisTemplate.delete(GET_ALL_STUDENT_KEY);
        stringRedisTemplate.delete(GET_UNION_STUDENT_KEY);
        return Restful.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/PutStudent")
    public Restful PutStudent(
            @RequestParam("name") String name,
            @RequestParam("gender")String gender,
            @RequestParam("manager")String manager,
            @RequestParam("age")int age,
            @RequestParam("fromClass")String fromClass,
            @RequestParam("PutUploadFile")MultipartFile multipartFile,
            @RequestParam("id")int id){
        Integer managerId = studentserviceimpl.QueryExitedManager(manager);
        if(managerId==null){return Restful.warning("班主任不存在,请重新输入");}
        Integer classId = studentserviceimpl.QueryExitedClass(fromClass);
        if(classId==null){return Restful.warning("班级不存在,请重新输入");}
        String filenmae = String.format("%s%s%s",name, UUID.randomUUID(),multipartFile.getOriginalFilename());
        String startfilename = studentserviceimpl.GetHeadImage(id);
        if(studentserviceimpl.PutStudent(name,gender,manager,age,fromClass,filenmae,managerId,classId,id)<=0){return Restful.error("修改失败");}
        try{
            Upload.UploadHeadImg(filenmae,multipartFile);
            log.info("{}文件上传成功",filenmae);
        }catch(Exception e){
            log.error("上传文件失败:{}",e.getMessage());
            return Restful.error("上传文件失败,请重新尝试");
        }
        if("".equals(startfilename) || startfilename==null){return Restful.success("修改成功");}
        try{
            Upload.DropHeadImg(startfilename);
            log.info("删除文件{}成功",startfilename);
        }catch(Exception e){
            log.info("删除文件{}失败:{}",startfilename,e.getMessage());
            return Restful.error("删除文件失败,请重新尝试");
        }
        stringRedisTemplate.delete(GET_ALL_STUDENT_KEY);
        stringRedisTemplate.delete(GET_UNION_STUDENT_KEY);
        return Restful.success();
    }

    @GetMapping("/Stu/GetGenderCount")
    public Restful GetGenderCount(){
        return Restful.success(rabbittemplate.convertSendAndReceive("ViewImage.topic","StudentKey","Count"),true);
    }
}
