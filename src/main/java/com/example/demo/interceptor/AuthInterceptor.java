package com.example.demo.interceptor;

import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行登录和注册接口
        String path = request.getRequestURI();
        if (path.contains("/api/users/login") || path.contains("/api/users") && request.getMethod().equals("POST")) {
            return true;
        }

        // 获取 Header 中的 Token
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录或Token已过期\",\"data\":null}");
            return false;
        }

        // 验证 Token
        String token = authHeader.substring(7);
        if (!JwtUtil.validateToken(token)) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"Token无效或已过期\",\"data\":null}");
            return false;
        }

        return true;
    }
}