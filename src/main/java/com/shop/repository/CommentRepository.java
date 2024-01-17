package com.shop.repository;

import com.shop.entity.Board;
import com.shop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBoardEntityOrderByIdDesc(Board boardEntity);

}
