package com.shop.service;

import com.shop.dto.CommentDTO;
import com.shop.entity.Board;
import com.shop.entity.Comment;
import com.shop.repository.BoardRepository;
import com.shop.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        // 부모 (CommentEntity) 엔티티 조회
        Optional<Comment> optionalParentComment = commentDTO.getParentCommentId() != null ?
                commentRepository.findById(commentDTO.getParentCommentId()) : Optional.empty();

        if (optionalParentComment.isPresent()) {
            // 부모 댓글이 존재하는 경우
            Comment parentComment = optionalParentComment.get();
            Comment commentEntity = Comment.toSaveEntity(commentDTO, parentComment.getBoardEntity(), parentComment);
            return commentRepository.save(commentEntity).getId();
        } else {
            // 부모 댓글이 없는 경우
            // 새로운 댓글을 생성하기 전에 부모 댓글을 찾아야 합니다.
            Optional<Board> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
            if (optionalBoardEntity.isPresent()) {
                Board boardEntity = optionalBoardEntity.get();
                // 부모 댓글은 null로 지정
                Comment commentEntity = Comment.toSaveEntity(commentDTO, boardEntity, null);
                return commentRepository.save(commentEntity).getId();
            } else {
                // 부모 및 게시글 엔티티가 없으면 null 반환
                return null;
            }
        }
    }
    public List<CommentDTO> findAll(Long boardId) {
        Board boardEntity = boardRepository.findById(boardId).get();
        List<Comment> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public Long saveReply(CommentDTO replyDTO, Long parentCommentId) {
        Optional<Comment> parentComment = commentRepository.findById(parentCommentId);
        if (parentComment.isPresent()) {
            Comment parent = parentComment.get();
            Comment reply = Comment.toSaveEntity(replyDTO, parent.getBoardEntity(), parent);
            return commentRepository.save(reply).getId();
        }
        return null;
    }

 }