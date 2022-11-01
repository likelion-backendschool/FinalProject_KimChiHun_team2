package com.ll.project.ebook.domain.member.service;

import com.ll.project.ebook.domain.member.dto.MemberDto;
import com.ll.project.ebook.domain.member.dto.MemberJoinDto;
import com.ll.project.ebook.domain.member.entity.Member;
import com.ll.project.ebook.domain.member.entity.Role;
import com.ll.project.ebook.domain.member.exception.AlreadyJoinException;
import com.ll.project.ebook.domain.member.exception.MemberNotFoundException;
import com.ll.project.ebook.domain.member.exception.PasswordNotMatchException;
import com.ll.project.ebook.domain.member.repository.MemberRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "doolysmile@gmail.com";
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

        // 메일 보내기
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(memberJoinDto.getEmail());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(memberJoinDto.getUsername() + "님 회원가입을 축하합니다");
        message.setText("회원 가입을 축하합니다." + "{사이트 링크}");
        javaMailSender.send(message);

        return memberRepository.save(memberJoinDto.toEntity(role)).getId();
    }



    public Long modify(Member member, String email, String nickname) {
        Role role = Role.MEMBER;
        if(!(nickname == null ||nickname.equals(""))){
            role = Role.AUTHOR;
        }
        member.modify(nickname, email,  role);
        return memberRepository.save(member).getId();
    }

    public Long modifyPassword(MemberDto memberDto, String oldPassword, String newPassword){
        Member member = findByUsername(memberDto.getUsername());
        if(member == null){
            throw new MemberNotFoundException("사용자를 찾을 수 없습니다.");
        }
        if(!checkPassword(memberDto, oldPassword)){
            throw new PasswordNotMatchException("기존 비밀번호가 다릅니다.");
        }
        member.setPassword(passwordEncoder.encode(newPassword));
        return memberRepository.save(member).getId();
    }
    public boolean checkPassword(MemberDto memberDto, String oldPassword) {
        Member member = findByUsername(memberDto.getUsername());
        if(member == null){
            throw new MemberNotFoundException("사용자를 찾을 수 없습니다.");
        }
        if(passwordEncoder.matches(oldPassword, member.getPassword())){
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Member findByUsername(String username) {
//        Member member = memberRepository.findByUsername(username).
//                orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다."));
        Member member = memberRepository.findByUsername(username).orElse(null);
        return member;
    }
    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
//        Member member = memberRepository.findByEmail(email).
//                orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다."));
        Member member = memberRepository.findByEmail(email).orElse(null);
        return member;
    }

    public Long findPassword(String username) {
        Member member = findByUsername(username);
        if(member == null){
            throw new MemberNotFoundException("사용자를 찾을 수 없습니다.");
        }
        String newPassword = generateRandomPassword(4);
        System.out.println("temp" + newPassword);
        member.setPassword(passwordEncoder.encode(newPassword));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(member.getEmail());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(member.getUsername() + "님 비밀번호입니다.");
        message.setText("임시비밀 번호는 : " + newPassword);
        javaMailSender.send(message);

        memberRepository.save(member);
        return member.getId();
    }
    // len은 비밀 번호의 길이
    public String generateRandomPassword(int len){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();

        return IntStream.range(0, len)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());

    }
}
