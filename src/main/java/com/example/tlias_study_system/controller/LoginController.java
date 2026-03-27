package com.example.tlias_study_system.controller;

import com.example.tlias_study_system.pojo.Restful;
import com.example.tlias_study_system.service.Impl.LoginServiceImpl;
import com.example.tlias_study_system.util.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginServiceImpl loginserviceimpl;
    public static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @PostMapping("/registers")
    public Restful Registers(@RequestBody Map<String,String> account){
        try{loginserviceimpl.Registers(account.get("accountFlag"),account.get("password"));}
        catch(Exception e){
            log.error("账号已存在:{}",e.getMessage());
            return Restful.error("账号已经存在，请重新输入");
        }
        return Restful.success();
    }
    @GetMapping("/login")
    public Restful Login(@RequestParam String accountFlag, @RequestParam String password){
        int existed;
        String token;
        try {
            existed = loginserviceimpl.QueryAccount(accountFlag, password);
            if(existed==0){
                log.info("账号为:{},密码为:{}的登录者登陆失败",accountFlag,password);
                return Restful.error("账号或密码错误,请重新输入");
            }
            token = Jwt.createJwt(accountFlag,password);
        }catch(Exception e){
            log.info("服务器错误:{}",e.getMessage());
            return Restful.warning("服务器繁忙,请稍后再试");
        }
        return Restful.success(token);
    }
    @PutMapping("/PutAccount")
    public Restful PutAccount(@RequestParam("accountFlag")String accountFlag, @RequestParam("password")String password){
        try{
            if(loginserviceimpl.QueryOnlyAccount(accountFlag)>=0){loginserviceimpl.PutAccount(accountFlag,password);}
            else{return Restful.error("账号不存在,请重新输入");}
        }
        catch(Exception e){
            log.info("accountFlag:{},password:{}修改密码失败",accountFlag,password);
            return Restful.error("服务器繁忙,请稍后再试");
        }
        return Restful.success();
    }
}
