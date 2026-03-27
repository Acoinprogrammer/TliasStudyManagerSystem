package com.example.tlias_study_system.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 使用 allowedOriginPatterns 而不是 allowedOrigins
                .allowedOriginPatterns("*")  // ✅ 允许所有域名（支持凭证）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)      // 允许携带凭证
                .maxAge(3600);
    }
}