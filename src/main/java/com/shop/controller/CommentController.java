package com.shop.controller;

import com.shop.dto.CommentDTO;
import com.shop.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {
        System.out.println("commentDTO =" + commentDTO);
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {//작성 성공하고 끝나면 기존 댓글이 화면에 새로 추가된 댓글이 목록에 다시 화면에 추가된걸 보여줘야한다
            //작성 성공 하면 댓글 목록을 가져와 리턴한다.
            //댓글 목록: 해당게시글  댓글 전체
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId()); //댓글 목록을 가저올땐 게시글 번호 기준이된다.
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getByBoardId/{boardId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByBoardId(@PathVariable Long boardId) {
        List<CommentDTO> commentDTOList = commentService.findAll(boardId);
        return ResponseEntity.ok(commentDTOList);
    }

    @PostMapping("/saveReply/{parentCommentId}")
    public ResponseEntity<Long> saveReply(@RequestBody CommentDTO replyDTO, @PathVariable Long parentCommentId) {
        Long replyId = commentService.saveReply(replyDTO, parentCommentId);
        if (replyId != null) {
            return ResponseEntity.ok(replyId);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
