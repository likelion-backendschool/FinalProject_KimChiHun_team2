package com.ll.project.ebook.domain.member.dto;


import com.ll.project.ebook.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    @NotEmpty(message = "아이디는 필수 입력 항목입니다.")
    private String username;
    @NotEmpty(message = "닉네임은 필수 입력 항목입니다.")
    private String nickname;
    @NotEmpty(message = "아이디는 필수 입력 항목입니다.")
    @Email(message = "이메일이 아닙니다(형식을 올바르게 입력해주세요)")
    private String email;
    private String author;

    public MemberInfoDto(Member member) {
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.author = member.getAuthor();
    }

}
