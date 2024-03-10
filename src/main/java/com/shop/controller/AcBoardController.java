package com.shop.controller;


import com.shop.dto.AcBoardDto;
import com.shop.entity.AcBoard;
import com.shop.entity.Member;
import com.shop.service.AcBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AcBoardController {
    private final AcBoardService acboardService;


    @RequestMapping(value = "/announcement")
    public String boardView(Model model, String id) {
        model.addAttribute("acboardlist", acboardService.acboardlist());
        return "announcement/aclist";
    }

    @GetMapping(value = "/acwriteForm")
    public String writeForm(Principal principal, Model model) {
        Member member = acboardService.findMembername(principal);
        model.addAttribute("memberlist", member);
        return "announcement/acwriteForm";
    }


    @PostMapping(value = "/acwrite")
    public String boardwrite(@ModelAttribute AcBoardDto acboardDto, Principal principal, @RequestParam("multipartFile") MultipartFile multipartFile) throws Exception {
        Member member = acboardService.findMembername(principal);
        AcBoard acboard = AcBoard.writeBoard(acboardDto,member);
        acboardService.writeBoard(acboard, multipartFile);
        return "redirect:announcement";
    }

    @GetMapping(value = "/announcement/acview")
    public String boardview(@RequestParam Long id, Model model) {
        model.addAttribute("acboard", acboardService.viewboard(id));
        return "announcement/acview";
    }


    @PostMapping("/announcement/delete")
    public String boardDelete(@RequestParam Long id) {
        acboardService.deleteBoard(id);
        return "redirect:/announcement";
    }

    @GetMapping("/announcement/acupdate/{id}")
    public String boardModify(@PathVariable Long id, Model model) {
        model.addAttribute("acboard", acboardService.viewboard(id));
        System.out.println("공지사항 수정확인"+acboardService.viewboard(id));
        return "announcement/acupdate";
    }

    @PostMapping("/announcement/acupdate/{id}")
    public String boardUpdate(@PathVariable Long id, AcBoard acboard, MultipartFile multipartFile) throws Exception {
        //기존에있던글이 담겨져서온다.
        AcBoard acboardTemp = acboardService.viewboard(id);

        //기존에있던 내용을 새로운 내용으로 덮어씌운다.
        acboardTemp.setTitle(acboard.getTitle());
        acboardTemp.setContent(acboard.getContent());


        acboardService.writeBoard(acboard, multipartFile);
        return "redirect:/announcement";
    }




}
