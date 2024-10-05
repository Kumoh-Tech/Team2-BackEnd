package com.study.board.entity;

import com.study.board.Constant.Role;
import com.study.board.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member { // 회원정보 저장 엔티티

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;  // 회원은 이메일을 통해 유일하게 구분하므로 unique 속성 지정

    private String password;

    private String major;

    @Enumerated(EnumType.STRING)  // enum의 순서가 바뀌지 않도록 하기 위해 enumType 을 String 으로 지정
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setMajor(memberFormDto.getMajor());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        // 스프링 시큐리티 설정 클래스에 등록한 BCryptPasswordEncoder Bean 을 파라미터로 넘겨서 비밀번호 암호화
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }

}
