package com.example.tlias_study_system.controller;

import com.example.tlias_study_system.pojo.Restful;
import com.example.tlias_study_system.service.Impl.ClassServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
public class ClassController {
    public static final Logger log = LoggerFactory.getLogger(ClassController.class);
    @Autowired
    private ClassServiceImpl classserviceimpl;
    @Autowired(required = false)
    public final StringRedisTemplate stringredistemplate = new StringRedisTemplate();

    public static final String GET_ALL_CLASS_KEY = "class:all";

    public static final String GET_ALL_CLASS_LOCK = "class:all:lock";

    public static final int GET_ALL_CLASS_LOCK_TIME = 5;

    public static final int GET_ALL_CLASS_KEY_TIME = 30;

    public static final ObjectMapper objectmapper = new ObjectMapper();

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/AddClass")
    public Restful AddClass(@RequestParam("name") String name, @RequestParam("teachername") String teachername){
        Integer Exited = classserviceimpl.QueryEmp(teachername);
        if(Exited==null){return Restful.error("添加失败,请确认当前班主任是否存在");}
        if(classserviceimpl.QueryRename(name)>0){return Restful.error("添加失败,请确认是否有重名班级");}
        int Addclass = classserviceimpl.AddClass(name,teachername,Exited);
        return Restful.success();
    }
    @GetMapping("/QueryClassCount")
    public Restful QueryClassCount(){return Restful.success(classserviceimpl.QueryClassCount());}

    @GetMapping("/LoadingClass")
    public Restful LoadingClass(@RequestParam int page, @RequestParam int size){
        return Restful.success(classserviceimpl.LoadingList(page-1,size));
    }
    @GetMapping("/GetAllClass")
    public Restful GetAllClass() throws JsonProcessingException, InterruptedException {
        List list = null;
        String total = stringredistemplate.opsForValue().get(GET_ALL_CLASS_KEY);
        if(total!=null && !total.isEmpty()){list = objectmapper.readValue(total,List.class);}
        if(list!=null){
            log.info("获取缓存成功,当前缓存为GET_ALL_CLASS_KEY的Value");
            return Restful.success(list);
        }
        try{
            if(tryLock()){
                Thread.sleep(100);
                return GetAllClass();
            }
        }catch(Exception e){
            log.error("缓存所有班级出现异常:{}",e.getMessage());
            throw new RuntimeException();
        }finally{unLock();}
        log.info("获取缓存失败，进行GetAllClass重建...");
        list = classserviceimpl.GetAllClass();
        stringredistemplate.opsForValue().set(GET_ALL_CLASS_KEY,objectmapper.writeValueAsString(list), Duration.ofMinutes(GET_ALL_CLASS_KEY_TIME));
        log.info("重建成功");
        return Restful.success(list);
    }

    public boolean tryLock(){return Boolean.TRUE.equals(stringredistemplate.opsForValue().setIfAbsent(GET_ALL_CLASS_LOCK,"1",Duration.ofSeconds(GET_ALL_CLASS_LOCK_TIME)));}

    public void unLock(){stringredistemplate.delete(GET_ALL_CLASS_LOCK);}

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/DelClass")
    public Restful DelClass(@RequestParam int classId) throws InterruptedException {
        String str = stringredistemplate.opsForValue().get(GET_ALL_CLASS_KEY);
        try{
            if(tryLock()){
                Thread.sleep(150);
                return DelClass(classId);
            }
        }catch(Exception e){log.info("服务器异常:{}",e.getMessage());}
        finally{unLock();}
        if(classserviceimpl.DelClass(classId)<=0){return Restful.warning("删除失败");}
        if(str!=null && !str.isEmpty()){stringredistemplate.delete(GET_ALL_CLASS_KEY);}
        return Restful.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/PutClass")
    public Restful PutClass(@RequestParam int classId, @RequestParam String name, @RequestParam int count, @RequestParam String manager) throws InterruptedException {
        String str = stringredistemplate.opsForValue().get(GET_ALL_CLASS_KEY);
        try{
            if(tryLock()){
                Thread.sleep(150);
                return PutClass(classId,name,count,manager);
            }
        }catch(Exception e){log.error("服务器异常:{}",e.getMessage());}
        finally{unLock();}
        Integer MId = classserviceimpl.QueryEmp(manager);
        if(MId==null){return Restful.error("当前班主任不存在,请重新输入");}
        int PutClass = classserviceimpl.PutClass(classId,name,count,manager,MId);
        if(PutClass<=0){return Restful.error("修改失败");}
        if(str!=null && !str.isEmpty()){stringredistemplate.delete(GET_ALL_CLASS_KEY);}
        return Restful.success();
    }
    @GetMapping("/ValueQuery")
    public Restful ValueQuery(@RequestParam("classId") int classId, @RequestParam("name") String name, @RequestParam("manager") String manager){return Restful.success(classserviceimpl.ValueQuery(classId,name,manager));}
}
