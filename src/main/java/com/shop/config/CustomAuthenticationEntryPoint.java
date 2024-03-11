package com.shop.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        // 아래 코드 두줄로 javascript의 alert를 html 파일이 아닌 class에서 실행가능한게 정말 놀랍다 ...
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<script>alert('로그인이 필요합니다'); window.location.href='/';</script>");

    }
}
