package com.example.tlias_study_system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@SpringBootTest
public class rEdis12 {
    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;
    @Test
    public void TEST() throws InterruptedException {
        redisTemplate.opsForValue().set("name11","tom111");
        System.out.println(redisTemplate.keys("*"));
        System.out.println(redisTemplate.opsForValue().get("name11"));
        String s = "1";
        redisTemplate.opsForValue().set("dep",s,Duration.ofMinutes(3));

        Thread.sleep(100000);
    }
}
