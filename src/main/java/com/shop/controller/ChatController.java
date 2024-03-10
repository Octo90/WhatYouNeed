package com.shop.controller;

import com.shop.entity.Member;
import com.shop.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final BoardService boardService;
    @GetMapping("/{id}")
    public String chattingRoom(@PathVariable String id, HttpSession session, Model model, Principal principal){
        Member member = boardService.findMembername(principal);

        if(id.equals("guest")){
            model.addAttribute("name", member.getName());
        }else if(id.equals("master")){
            model.addAttribute("name", "운영자");
        }else if(id.equals("loose")){
            model.addAttribute("name", "loose");
        }else {
            return "error";
        }
        return "Chat/chattingRoom2";
    }
}
