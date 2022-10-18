package com.ll.project.ebook.domain.member.service;

import com.ll.project.ebook.domain.member.dto.MemberJoinDto;
import com.ll.project.ebook.domain.member.entity.Member;
import com.ll.project.ebook.domain.member.entity.Role;
import com.ll.project.ebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public Long join(MemberJoinDto memberJoinDto) {
        memberJoinDto.setPassword1(passwordEncoder.encode(memberJoinDto.getPassword1()));
        Role role = Role.MEMBER;
        if(memberRepository.findByUsername(memberJoinDto.getUsername()).isPresent()){
            throw new ArithmeticException();
        }
        // 작가명이 있으면 author;
        if(!(memberJoinDto.getAuthor() == null ||memberJoinDto.getAuthor().equals(""))){
            role = Role.AUTHOR;
        }

        return memberRepository.save(memberJoinDto.toEntity(role)).getId();
    }
}
