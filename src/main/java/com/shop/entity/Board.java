package com.shop.entity;

import com.shop.dto.BoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String password;
    @Transient
    @Column(nullable = false)
    private String passwordConfirm;

    private String imgName;
    private String oriImgName;
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;




    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentEntityList = new ArrayList<>();
    public static Board writeBoard(BoardDto boardDto, PasswordEncoder passwordEncoder, Member member){
        Board board = new Board();
        board.setName(member.getName());
        board.setTitle(boardDto.getTitle());
        String password = passwordEncoder.encode(boardDto.getPassword());
        board.setPassword(password);
        board.setContent(boardDto.getContent());
        return board;
    }

    public static Board toUpdateEntity(BoardDto boardDTO) {
        Board boardEntity = new Board();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setName(boardDTO.getName());
        boardEntity.setPassword(boardDTO.getPassword());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        return boardEntity;

    }

    public void updateBoardImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }


}
