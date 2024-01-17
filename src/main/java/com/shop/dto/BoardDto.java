package com.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.shop.entity.Board;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private Long number;
    private Long id;
    private String name;
    private String title;
    private String content;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String passwordConfirm;


    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;





    public static BoardDto toBoardDTO(Board boardEntity) {
        BoardDto boardDTO = new BoardDto();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setName(boardEntity.getName());
        boardDTO.setPassword(boardEntity.getPassword());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setContent(boardEntity.getContent());

        return boardDTO;
    }





}
