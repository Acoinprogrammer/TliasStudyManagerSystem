package com.example.tlias_study_system.controller;

import com.example.tlias_study_system.service.Impl.EmpServiceImpl;
import com.example.tlias_study_system.service.Impl.StudentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQController.class);
    @Autowired
    private EmpServiceImpl empserviceimpl;
    @Autowired
    private StudentServiceImpl studentserviceimpl;
    private static final ObjectMapper objectmapper = new ObjectMapper();
    @RabbitListener(queues = "EmpMQ")
    public String EmpMQGet(String message) throws JsonProcessingException {
        if(message.equals("Count")){
            String count = objectmapper.writeValueAsString(empserviceimpl.GetGenderCount());
            log.info("EmpMQ return {}",count);
            return count;
        }
        return null;
    }
    @RabbitListener(queues = "StudentMQ")
    public String StudentMQGet(String message) throws JsonProcessingException{
        if(message.equals("Count")){
            String count = objectmapper.writeValueAsString(studentserviceimpl.GetGenderCount());
            log.info("StudentMQ return {}",count);
            return count;
        }
        return null;
    }
}
