package com.ll.project.ebook.domain.member.controller;

import com.ll.project.ebook.domain.member.dto.MemberInfoDto;
import com.ll.project.ebook.domain.member.dto.MemberJoinDto;
import com.ll.project.ebook.domain.member.service.MemberService;
import com.ll.project.ebook.global.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin(MemberJoinDto memberJoinDto) {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid MemberJoinDto memberJoinDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("memberJoinDto", memberJoinDto);
            return "member/join";
        }

        if(!memberJoinDto.getPassword1().equals(memberJoinDto.getPassword2())){
            bindingResult.rejectValue("password2", "passwordIncorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "member/join";
        }

        memberService.join(memberJoinDto);

        return "redirect:/member/login?msg=" + Ut.url.encode("회원가입이 완료되었습니다.");
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin(HttpServletRequest request) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/member/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        model.addAttribute("memberInfoDto",  memberService.findByUsername(principal.getName()));
        return "member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String showModify(Model model, Principal principal) {
        model.addAttribute("memberInfoDto",  memberService.findByUsername(principal.getName()));
        return "member/infoModify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@Valid MemberInfoDto memberInfoDto, BindingResult bindingResult, Model model, Principal principal) {

        System.out.println("qqqqqqqqqqqqq" + memberInfoDto.getAuthor());
        if(bindingResult.hasErrors()){
            model.addAttribute("memberInfoDto", memberService.findByUsername(principal.getName()));
            return "member/infoModify";
        }

        model.addAttribute("memberInfoDto",  memberService.findByUsername(principal.getName()));
        memberService.modify(memberInfoDto);

        return "redirect:/member/profile?msg=" + Ut.url.encode("수정이 완료되었습니다.");
    }
}
