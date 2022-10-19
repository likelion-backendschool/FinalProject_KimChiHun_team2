package com.ll.project.ebook.domain.member.service;

import com.ll.project.ebook.domain.member.dto.MemberDto;
import com.ll.project.ebook.domain.member.dto.MemberInfoDto;
import com.ll.project.ebook.domain.member.dto.MemberJoinDto;
import com.ll.project.ebook.domain.member.entity.Member;
import com.ll.project.ebook.domain.member.entity.Role;
import com.ll.project.ebook.domain.member.exception.AlreadyJoinException;
import com.ll.project.ebook.domain.member.exception.MemberNotFoundException;
import com.ll.project.ebook.domain.member.repository.MemberRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public Long join(MemberJoinDto memberJoinDto) {
        memberJoinDto.setPassword1(passwordEncoder.encode(memberJoinDto.getPassword1()));
        Role role = Role.MEMBER;
        if(memberRepository.findByUsername(memberJoinDto.getUsername()).isPresent()){
            throw new AlreadyJoinException();
        }
        // 작가명이 있으면 author;
        if(!(memberJoinDto.getNickname() == null ||memberJoinDto.getNickname().equals(""))){
            role = Role.AUTHOR;
        }

        return memberRepository.save(memberJoinDto.toEntity(role)).getId();
    }
    @Transactional(readOnly = true)
    public MemberInfoDto findByUsername(String username) {
        Member member = memberRepository.findByUsername(username).
                orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다."));

        return new MemberInfoDto(member);
    }

    @Transactional(readOnly = true)
    public Member find(String username) {
        Member member = memberRepository.findByUsername(username).
                orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다."));

        return member;
    }

    public Long modify(Member member, String email, String nickname) {
        Role role = Role.MEMBER;
        if(!(nickname == null ||nickname.equals(""))){
            role = Role.AUTHOR;
        }
        member.modify(nickname, email,  role);
        return memberRepository.save(member).getId();
    }
}
