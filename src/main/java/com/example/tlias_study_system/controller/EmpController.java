package com.example.tlias_study_system.controller;

import com.example.tlias_study_system.pojo.employee;
import com.example.tlias_study_system.pojo.Restful;
import com.example.tlias_study_system.service.EmpService;
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

import java.util.List;
import java.util.UUID;

@RestController
public class EmpController {
    private static final Logger log = LoggerFactory.getLogger(EmpController.class);
    @Autowired
    private EmpService empservice;

    @Autowired(required = false)
    private RabbitTemplate rabbittemplate;

    @Autowired(required=false)
    private StringRedisTemplate stringRedisTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();
    @GetMapping("/GetPageSizeEmp")
    public Restful GetPageSizeEmp(@RequestParam int page, @RequestParam int size){
        int count = empservice.GetAllEmpCount();
        if(count<(page-1)*size){
            log.debug("beyond employee len");
            return Restful.warning();
        }
        List<employee> list = empservice.GetPageSizeEmp(page,size);
        return Restful.success(list);
    }
    @GetMapping("/GetAllEmpCount")
    public Restful GetAllEmpCount(){return Restful.success(empservice.GetAllEmpCount());}
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/DeleteEmp")
    public Restful DeleteEmp(@RequestParam int id){
        log.info("id {}",id);
        try{
            Upload.DropHeadImg(empservice.QueryHeadImg(id));}
        catch(Exception e){
            log.error("服务器异常,请检查阿里云链接配置:{}",e.getMessage());
            return Restful.error("服务器错误,请联系管理员");
        }
        int dropemp = empservice.DeleteEmp(id);
        if(dropemp<=0){
            log.info("id为{}的员工已经被删除了",id);
            return Restful.error("当前员工已经被删除");
        }
        return Restful.success(dropemp,"删除成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/InsertEmp")
    public Restful InsertEmp(
            @RequestParam("UploadFile") MultipartFile UploadFile,
            @RequestParam("name") String name,
            @RequestParam("gender") String gender,
            @RequestParam("position") String position,
            @RequestParam("phone") String phone,
            @RequestParam("deptId") String deptId
        ) throws Exception {
        Integer query_deptId = empservice.SelectDeptId(deptId);
        if(empservice.SelectIsSamePhone(phone)>0){return Restful.error("手机号码重复,请重新输入");}
        for(char c : phone.toCharArray()){if(c < '0' || c > '9'){return Restful.error("手机号码格式错误,请重新输入");}}
        String filename = String.format("%s%s%s",name,UUID.randomUUID().toString(),UploadFile.getOriginalFilename());
        try {
            Upload.UploadHeadImg(filename, UploadFile);
            log.info("文件上传完成: {}", filename);
        } catch (Exception e) {
            log.error("文件上传失败: {}", filename, e);
            return Restful.error("文件上传错误,请联系管理员");
        }
        if(empservice.InsertEmp(name,query_deptId==null?0:query_deptId,gender,phone,position,filename)<=0){return Restful.error("服务器端错误,请联系管理员");}
        return Restful.success("上传成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/UpdateEmp")
    public Restful UpdateEmp(
            @RequestParam("UploadFile")MultipartFile UploadFile,
            @RequestParam("name")String name,
            @RequestParam("gender")String gender,
            @RequestParam("position")String position,
            @RequestParam("phone")String phone,
            @RequestParam("deptId")String deptId,
            @RequestParam("id")int id
    ) throws Exception {
        if(empservice.SelectIsSamePhone(phone)>0){return Restful.error("手机号码重复,请重新输入");}
        for(char c : phone.toCharArray()){if(c < '0' || c > '9'){return Restful.error("手机号码格式错误,请重新输入");}}
        String filename = String.format("%s%s%s",name,UUID.randomUUID().toString(),UploadFile.getOriginalFilename());
        try{
            Upload.UploadHeadImg(filename,UploadFile);}
        catch(Exception e){
            log.error("{}上传失败",filename);
            return Restful.error("上传文件失败,请重新尝试");
        }
        try{
            Upload.DropHeadImg(empservice.QueryHeadImg(id));}
        catch(Exception e){
            try{
                Upload.DropHeadImg(filename);}
            catch(Exception e1){
                 log.error("删除失败:{}",e1.getMessage());
                 return Restful.error("服务器错误,请联系管理员");
            }
            log.error("服务器异常,请检查阿里云链接配置:{}",e.getMessage());
            return Restful.error("服务器错误,请联系管理员");
        }
        Integer querydeptid = empservice.SelectDeptId(deptId);
        if(empservice.UpdateEmp(id,name,gender,phone,querydeptid==null?0:querydeptid,position,filename)<=0){return Restful.error("服务器错误,请联系管理员");}
        return Restful.success("修改成功");
    }
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/QueryEmp")
    public Restful QueryEmp(
            @RequestParam("id")int id,
            @RequestParam("name")String name,
            @RequestParam("phone")String phone,
            @RequestParam("deptId")String deptId,
            @RequestParam("position")String position,
            @RequestParam("page")int page,
            @RequestParam("count")int count,
            @RequestParam("size")int size
    ){
        double stime = System.currentTimeMillis();
        Integer querydept = empservice.SelectDeptId(deptId);
        List<employee> querylist;
        try {querylist = empservice.QueryEmp(id, name, phone, position, querydept == null ? 0 : querydept, page, count, size);}
        catch(Exception e){return Restful.error("服务器错误,请稍后再试");}
        double etime = System.currentTimeMillis();
        log.info("QueryEmp run time cost {} second",(etime-stime)/1000);
        return Restful.success(querylist);
    }


    @GetMapping("/GetGenderCount")
    public Restful GetGenderCount() throws JsonProcessingException {
        return Restful.success(rabbittemplate.convertSendAndReceive("ViewImage.topic","EmpKey","Count"),true);
    }
}
