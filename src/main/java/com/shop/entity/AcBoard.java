package com.shop.entity;

import com.shop.dto.AcBoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AcBoard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private String imgName;
    private String oriImgName;
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    /*@OneToMany(mappedBy = "acboard", cascade = CascadeType.ALL, orphanRemoval = true)
    // cascade = CascadeType.ALL, orphanRemoval = true 이란것은 부모엔티티의 변경이 자식 엔티티에도 전파되는 느낌.
    private List<AcComment> commentList = new ArrayList<>(); */


    public static AcBoard writeBoard(AcBoardDto acboardDto, Member member){
        AcBoard acboard = new AcBoard();
        acboard.setName(member.getName());
        acboard.setTitle(acboardDto.getTitle());
        acboard.setContent(acboardDto.getContent());
        return acboard;
    }



    public void updateAcBoardImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }


}
