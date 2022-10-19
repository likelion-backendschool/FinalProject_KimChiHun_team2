package com.ll.project.ebook.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.project.ebook.global.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;


    @Enumerated(EnumType.STRING)
    private Role role;

    public void modify(String nickname, String email, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }
}
