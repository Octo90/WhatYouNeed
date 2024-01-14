package com.shop.controller;

import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.service.ItemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemService itemService;
    @GetMapping(value = "/")
    public String main(Model model){
        List<MainItemDto> items = itemService.getMainItemList();
        model.addAttribute("items", items);
        return "main";
    }

    @GetMapping(value = "/about")
    public String about(){
        return "/others/about";
    }
}
