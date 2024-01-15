package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 나 서비스다.
@Transactional // 트랜젝션설정 : 성공을하면 그대로 적용 실패하면 롤백
@RequiredArgsConstructor // final 또는 @NonNull 명령어가 붙으면 객체를 자동 붙혀줍니다.
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    //    @Autowired
//    MemberRepository memberRepository;
    public Member saveMember(Member member) {
        return memberRepository.save(member); // 데이터베이스에 저장을 하라는 명령
    }
    public void validateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail()).orElseThrow();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        //빌더패턴
        return User.builder().username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
