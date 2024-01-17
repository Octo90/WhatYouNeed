package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class CommentDTO {
    private Long id;
    private String commentWriter;

    private  String commentContents;

    private  Long boardId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDateTime commentCreatedTime;

    private Long parentCommentId;
    public static CommentDTO toCommentDTO(Comment commentEntity, Long boardId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());

        commentDTO.setCommentCreatedTime(commentEntity.getRegTime());
        commentDTO.setBoardId(boardId);

        if (commentEntity.getParentCommentId() != null) {
            commentDTO.setParentCommentId(commentEntity.getParentCommentId().getId());
        }
        return commentDTO;
    }


    }

