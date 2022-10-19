package com.ll.project.ebook.domain.member.dto;

import com.ll.project.ebook.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class MemberDto extends User {
    private final Long id;
    private final LocalDateTime createDate;
    @Setter
    private LocalDateTime modifyDate;
    private final String username;
    private final String nickname;
    private final String email;
    private Map<String, Object> attributes;

    public MemberDto(Member member, List<GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }

    public Member getMember() {
        return Member
                .builder()
                .id(id)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .username(username)
                .nickname(nickname)
                .email(email)
                .build();
    }

    public String getName() {
        return getUsername();
    }
    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return super.getAuthorities().stream().collect(Collectors.toSet());
    }

}
