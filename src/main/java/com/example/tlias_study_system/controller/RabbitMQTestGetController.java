package com.example.tlias_study_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/RabbitMQGet")
public class RabbitMQTestGetController {
    public static final Logger log = LoggerFactory.getLogger(RabbitMQTestGetController.class);
    @RabbitListener(queues = "TestMQ")
    public void TestMQGet(String message) throws InterruptedException{
        try{log.info("spring cost's method get message:{}",message);}
        catch(Exception e){log.info("RabbitMQ get message Exception:{}",e.getMessage());}
    }
}
