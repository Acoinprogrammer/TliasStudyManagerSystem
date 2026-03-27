package com.example.tlias_study_system.controller;


import com.example.tlias_study_system.pojo.Restful;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/RabbitMQSend")
public class RabbitMQTestSendController {
    public static final Logger log = LoggerFactory.getLogger(RabbitMQTestSendController.class);
    @Autowired
    private RabbitTemplate rabbittemplate;
    @GetMapping("TestMQSend")
    public Restful TestMQSend(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info("This is a message at {}",sdf.format(new Date()));
            rabbittemplate.convertAndSend("TestMQ.topic","TestMQKey",String.format("This is a message at %s",sdf.format(new Date())));
        }catch(Exception e){
            log.info("RabbitMQ send message Exception:{}",e.getMessage());
        }
        return Restful.success("RabbitMQ send now message successful");
    }
}
