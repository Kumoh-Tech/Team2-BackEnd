package com.study.board.api;

import com.study.board.Service.MemberService;
import com.study.board.dto.MemberFormDto;
import com.study.board.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor  //REST API 의 엔드포인트를 명시적으로 선언
public class MemberApiController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 폼을 반환하지 않고, JSON 형식으로 데이터를 전달 받음
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody MemberFormDto memberFormDto, BindingResult bindingResult){
        // 검증하려는 객체 앞에 @Valid 어노테이션 선언,
        // @ResquestBody 를 통해 클라이언트로부터 JSON 데이터를 받음
        // 파라미터로 bindingResult 객체 추가

        // 검사 후 결과는 bindingResult 에 담아둠
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors()); // 에러 발생시 JSON 형식으로 반환
        }

        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member); //회원 저장
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // 중복 회원 등 예외처리
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다."); // 회원가입 성공 시 응답
    }

    @GetMapping("/signup")
    public ResponseEntity<MemberFormDto> memberForm(){
        MemberFormDto memberFormDto = new MemberFormDto();
        return ResponseEntity.ok(memberFormDto);
    }

}
