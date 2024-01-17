package com.shop.repository;

import com.shop.dto.BoardDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
        List<Board> findAll();
        Optional<Board> findById(Long id);
        List<Board> findByIdOrderByIdAsc(Long Id);

}
