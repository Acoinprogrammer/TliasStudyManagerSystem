package com.example.tlias_study_system.controller;

import com.example.tlias_study_system.pojo.Restful;
import com.example.tlias_study_system.pojo.student;
import com.example.tlias_study_system.service.Impl.StudentServiceImpl;
import com.example.tlias_study_system.util.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
public class StudentLogin {
    public static final Logger log = LoggerFactory.getLogger(StudentLogin.class);
    @Autowired
    private StudentServiceImpl studentserviceimpl;
    @GetMapping("/StudentLogin")
    public Restful StudentLogin(@RequestParam String id, @RequestParam String name){
        List<student> list = studentserviceimpl.StudentLogin(id,name);
        if(list==null || list.size()==0){
            log.error("id为{},name为{}的学生尝试登陆,登陆失败",id,name);
            return Restful.error("用户名或密码错误");
        }
        String token = Jwt.createJwt(id,name);
        return Restful.success(list,token,"登陆成功");
    }
}
