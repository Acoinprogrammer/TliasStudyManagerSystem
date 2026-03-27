package com.example.tlias_study_system;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class RabbitMQTest {
    @Autowired(required = false)
    private RabbitTemplate rabbittemplate;
    public static final Logger log = LoggerFactory.getLogger(RabbitMQTest.class);
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Test
    void TestRabbitMQSendMessage(){
//        rabbittemplate.convertAndSend("amq.direct","TestMQKey",String.format("This is a message at %s",sdf.format(new Date())));
        rabbittemplate.convertAndSend("TestMQ",String.format("This is a message at %s",sdf.format(new Date())));
    }
}
