package com.example.tlias_study_system;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class jwt{
    public static final String JWT_PASSWORD = "tlias_manager_system";
    public static final Logger log = LoggerFactory.getLogger(jwt.class);

    @Test
    void createJWT(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("name1","1");
        claims.put("name2","2");
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256,JWT_PASSWORD)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+60*1000))
                .compact();
        log.info("{}",jwt);
    }
    @Test
    void checkJWT(){
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lMiI6IjIiLCJuYW1lMSI6IjEiLCJleHAiOjE3NzI1ODMzOTV9.DCMLSFpLYS7IKth7ZPFUKfOYLRU3T_NlSc_tH_A9iWc";
        Claims claims = Jwts.parser().setSigningKey(JWT_PASSWORD)
                .parseClaimsJws(jwt)
                .getBody();
        log.info("{}",claims);
    }
}