package com.shop.config;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.security.Principal;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    public void handleNullPointerException(NullPointerException ex, Model model, HttpServletResponse response,  Principal principal) throws IOException {

        if (principal == null){
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<script>alert('로그인이 필요합니다'); window.location.href='/';</script>");
        }
        else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<script>alert('문제가 발생했습니다. 관리자에게 문의 하세요 . '); window.location.href='/';</script>");
        }
    }
}
