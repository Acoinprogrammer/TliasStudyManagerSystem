package com.example.tlias_study_system.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Jwt {
    public static final String SAME_KEY = "tlais_manager_system";
    public static final Logger log = LoggerFactory.getLogger(Jwt.class);
    public static String createJwt(String accountFlag,String password){
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,SAME_KEY)
                .claim(accountFlag,password)
                .setExpiration(new Date(System.currentTimeMillis()+1000*60))
                .compact();
        log.info("令牌生成:{}",token);
        return token;
    }
    public static boolean checkJwt(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(SAME_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch(Exception e){
            log.info("Token解析失败:{}",e.getMessage());
            return false;
        }
        return true;
    }
}
