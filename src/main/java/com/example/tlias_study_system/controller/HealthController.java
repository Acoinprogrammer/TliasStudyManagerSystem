package com.example.tlias_study_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "*")
public class HealthController {
    public static final Logger log = LoggerFactory.getLogger(HealthController.class);
    @RequestMapping("/health")
    public void health(){
        log.info("check now health");
    }
}
