package com.ja.finalproject.global.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        if(request.getSession().getAttribute("sessionUserInfo") == null) {
            response.sendRedirect("/user/sessionNullPage");
            return false;
        }
        
        return true;
    }
}
