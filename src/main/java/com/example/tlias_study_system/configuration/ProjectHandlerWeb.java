package com.example.tlias_study_system.configuration;

import com.example.tlias_study_system.util.ProjectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ProjectHandlerWeb implements WebMvcConfigurer {
    @Autowired
    private ProjectInterceptor projectInterceptor;
    public void addInterceptors(InterceptorRegistry registry) {registry.addInterceptor(projectInterceptor).addPathPatterns("/health");}
}
