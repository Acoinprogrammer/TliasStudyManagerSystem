package com.example.tlias_study_system.util;

import com.example.tlias_study_system.service.Impl.LoginServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ProjectInterceptor implements HandlerInterceptor {
    public static final Logger log = LoggerFactory.getLogger(ProjectInterceptor.class);
    @Autowired
    private LoginServiceImpl loginserviceimpl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI(),token = request.getHeader("token"),method = request.getMethod();
        log.info("{}",request.getHeaderNames().toString());
        log.info("{} {}",url,method);
        if ("OPTIONS".equalsIgnoreCase(method)) {
            log.info("OPTIONS 请求放行");
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
//        accountFlag = request.getHeader("accountFlag");
        log.info("url:{},token:{}",url,token);
//        if(!(Jwt.checkJwt(token) && loginserviceimpl.QueryOnlyAccount(accountFlag)>0))
        if(!Jwt.checkJwt(token)){
                response.setContentType("application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(403);
                response.getWriter().write("登陆状态异常,请重新登陆");
                return false;
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
