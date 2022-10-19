package com.ll.project.ebook.domain.member.dto;

import com.ll.project.ebook.domain.member.entity.Member;
import com.ll.project.ebook.domain.member.entity.Role;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinDto {

    @NotEmpty(message = "아이디는 필수 입력 항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String password2;

    @NotEmpty(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;

    @NotEmpty(message = "아이디는 필수 입력 항목입니다.")
    @Email(message = "이메일이 아닙니다(형식을 올바르게 입력해주세요)")
    private String email;
    public Member toEntity(Role role){
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .password(password1)
                .email(email)
                .role(role)
                .build();
    }
}