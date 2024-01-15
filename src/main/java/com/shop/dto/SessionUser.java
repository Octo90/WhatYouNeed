package com.shop.dto;

import com.shop.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;        // 직렬화
@Getter
@Setter
@NoArgsConstructor      // Default 생성자 생성
// 직렬화 -> 자바 시스템에서 사용 할 수 있도록 바이트 스트림 형태로 연속적인 데이터로 포맷 변환 기술
// 직렬화 = Byte Stream <- 자자 Object Data
// 역직렬화 = 바이트 스트림 -> 자바 Object Data
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(Member member){
        this.name=member.getName();
        this.email=member.getEmail();
    }
}
