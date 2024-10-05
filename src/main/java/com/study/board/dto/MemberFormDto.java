package com.study.board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.") // NULL 체크 및 문자열의 경우 길이 0 및 빈 반자열(" ") 인지 검사
    private String name;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.") // NULL 체크 및 문자열의 경우 길이가 0인지 검사
    @Email(message = "이메일 형식으로 입력해주세요.") // 이메일 형식인지 검사
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16, message="비밀번호는 8자 이상, 16자 이하로 입력해주세요") // 최소, 최대 길이 검사
    private String password;

    @NotEmpty(message = "학과는 필수 입력 값입니다.")
    private String major;

}
