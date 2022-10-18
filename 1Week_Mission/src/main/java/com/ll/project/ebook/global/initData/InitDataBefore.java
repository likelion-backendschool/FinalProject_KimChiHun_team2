package com.ll.project.ebook.global.initData;

import com.ll.project.ebook.domain.member.dto.MemberJoinDto;
import com.ll.project.ebook.domain.member.entity.Member;
import com.ll.project.ebook.domain.member.entity.Role;
import com.ll.project.ebook.domain.member.service.MemberService;

public interface InitDataBefore {
    default void before(MemberService memberService) {
        MemberJoinDto member1 = new MemberJoinDto("user1", "1234","1234", "qwe", "test1@test.com", "");
        MemberJoinDto member2 = new MemberJoinDto("user2", "1234","1234", "asd", "test2@test.com", "auth");
        memberService.join(member1);
        memberService.join(member2);
    }
}
