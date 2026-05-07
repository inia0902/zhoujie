package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("=== SecurityConfig.securityFilterChain() 被调用 ===");
        http
                // 开启全局 CORS 配置
                .cors(Customizer.withDefaults())
                // 关闭 CSRF 防护，前后端分离项目不需要
                .csrf(AbstractHttpConfigurer::disable)
                // 配置 Session 管理策略，设置无状态会话
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 重点：配置接口访问规则
                .authorizeHttpRequests(auth -> auth
                        // 放行注册接口
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        // 放行登录接口
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        // 其他所有请求都必须先认证
                        .anyRequest().authenticated()
                )
                // 关闭 Spring Security 默认的表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 关闭 httpBasic 默认认证
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
