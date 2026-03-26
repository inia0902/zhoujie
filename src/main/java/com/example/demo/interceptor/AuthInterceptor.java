package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.PrintWriter;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 放行：查询用户 GET /api/users/{id}、注册用户 POST /api/users
        if (("GET".equalsIgnoreCase(method) && uri.startsWith("/api/users/"))
                || ("POST".equalsIgnoreCase(method) && "/api/users".equals(uri))) {
            return true;
        }

        // 校验 token
        String token = request.getHeader("Authorization");
        if (token == null || token.isBlank()) {
            response.setContentType("application/json;charset=UTF-8");

            try (PrintWriter writer = response.getWriter()) {
                writer.write("{\"code\":401,\"msg\":\"登录凭证已缺失，请重新登录\"}");
                writer.flush();
            }
            return false;
        }

        return true;
    }
}