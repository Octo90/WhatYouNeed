package com.shop.controller;

import com.shop.dto.BoardDto;
import com.shop.entity.Board;
import com.shop.entity.Member;
import com.shop.service.BoardService;
import com.shop.service.HttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final PasswordEncoder passwordEncoder;
    private final HttpService httpService;

    @RequestMapping(value = "/board")
    public String boardView(Model model, String id) {
        model.addAttribute("boardlist", boardService.boardlist());
        return "board/list";
    }

    @GetMapping(value = "/writeForm")
    public String writeForm(Principal principal, Model model) {
        Member member = boardService.findMembername(principal);
        model.addAttribute("memberlist", member);
        return "board/writeForm";
    }


    @PostMapping(value = "/write")
    public String boardwrite(@ModelAttribute BoardDto boardDto, Principal principal, @RequestParam("multipartFile") MultipartFile multipartFile) throws Exception {
        Member member = boardService.findMembername(principal);
        Board board = Board.writeBoard(boardDto, passwordEncoder, member);
        boardService.writeBoard(board, multipartFile);
        return "redirect:board";
    }

    @GetMapping(value = "/board/view")
    public String boardview(@RequestParam Long id, Model model) {
        model.addAttribute("board", boardService.viewboard(id));

        return "board/view";
    }


    @PostMapping("/board/delete")
    public String boardDelete(@RequestParam Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board";
    }

    @GetMapping("/board/update/{id}")
    public String boardModify(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.viewboard(id));

        return "board/update";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable Long id, Board board, MultipartFile multipartFile) throws Exception {
        //기존에있던글이 담겨져서온다.
        Board boardTemp = boardService.viewboard(id);

        //기존에있던 내용을 새로운 내용으로 덮어씌운다.
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        String encodedPassword = passwordEncoder.encode(board.getPassword());
        board.setPassword(encodedPassword);

        boardService.writeBoard(board, multipartFile);
        return "redirect:/board";
    }

    @PostMapping("/board/checkPassword")
    public ResponseEntity<Map<String, Boolean>> checkPassword(@RequestParam Long postId, @RequestParam String password) {
        Optional<Board> boardOptional = boardService.getBoardById(postId);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            // 비밀번호가 null인지 확인
            if (board.getPassword() == null) {
                // null이면 비밀번호 확인 실패 처리
                Map<String, Boolean> response = new HashMap<>();
                response.put("success", false);
                return ResponseEntity.ok(response);
            }
            boolean passwordMatch = passwordEncoder.matches(password, board.getPassword());
            Map<String, Boolean> response = new HashMap<>();
            response.put("success", passwordMatch);
            return ResponseEntity.ok(response);
        } else {
            // 해당 게시글이 없는 경우에 대한 처리
            return ResponseEntity.notFound().build();
        }
    }




}

