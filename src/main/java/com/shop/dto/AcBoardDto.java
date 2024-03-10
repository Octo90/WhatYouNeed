package com.shop.dto;

import com.shop.entity.AcBoard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcBoardDto {
    private Long number;
    private Long id;
    private String name;
    private String title;
    private String content;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;





    public static AcBoardDto toAcBoardDTO(AcBoard acboardEntity) {
        AcBoardDto AcboardDTO = new AcBoardDto();
        AcboardDTO.setId(acboardEntity.getId());
        AcboardDTO.setName(acboardEntity.getName());
        AcboardDTO.setTitle(acboardEntity.getTitle());
        AcboardDTO.setContent(acboardEntity.getContent());

        return AcboardDTO;
    }



}
