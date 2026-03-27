package com.example.tlias_study_system;

import com.example.tlias_study_system.mapper.DeptMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.text.ParseException;

@SpringBootTest
public class redistest {
    @Autowired(required = false)
    private StringRedisTemplate stringredisTemplate;
    @Autowired(required = false)
    private StringRedisTemplate stringredistemplate;
    @Autowired(required = false)
    private DeptMapper deptmapper;
    private static final ObjectMapper om = new ObjectMapper();
    @Test
    void testString() throws InterruptedException, ParseException, JsonProcessingException {
//        Date d1 = new SimpleDateFormat().parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        LocalDateTime l = d1.toInstant()
//                .atZone(ZoneId.systemDefault())
//                .toLocalDateTime();
//        String l = LocalDateTime.now().toString();
//        dept d = new dept(1,"tlias",l,l);
//        String json = om.writeValueAsString(d);
//        stringredistemplate.opsForValue().set("dept:1",json);
//        String jsonUser = stringredisTemplate.opsForValue().get("dept:1");
//        dept d1 = om.readValue(jsonUser,dept.class);
//        System.out.println(d1.toString());
        String value = String.valueOf(deptmapper.GetAllDeptCount());
        stringredistemplate.opsForValue().set("dept:count",value);
        System.out.println(stringredistemplate.opsForValue().get("dept:count"));

    }
}
