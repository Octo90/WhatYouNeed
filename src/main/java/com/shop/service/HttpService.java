package com.shop.service;

import com.shop.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class HttpService {
    private final HttpSession httpSession;

    public String principalEmail(Principal principal){
        if(httpSession.getAttribute("USER") != null){
            return ((SessionUser)httpSession.getAttribute("USER")).getEmail();
        }
        return principal.getName();
    }
}
