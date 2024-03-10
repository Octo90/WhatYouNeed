package com.shop.repository;

import com.shop.entity.AcBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AcBoardRepository extends JpaRepository<AcBoard, Long> {
    List<AcBoard> findAll();
    Optional<AcBoard> findById(Long id);
}
