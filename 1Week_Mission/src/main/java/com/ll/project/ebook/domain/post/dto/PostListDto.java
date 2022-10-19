package com.ll.project.ebook.domain.post.dto;

import com.ll.project.ebook.domain.member.entity.Member;
import com.ll.project.ebook.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListDto {
    private Long id;
    private LocalDateTime modifyDate;
    private Member member;
    private String subject;
    private String content;
    private String contentHtml;

    public PostListDto(Post post){
        this.id = post.getId();
        this.modifyDate = post.getModifyDate();
        this.member = post.getMember();
        this.subject = post.getSubject();
        this.content = post.getContent();
        this.contentHtml = post.getContentHtml();
    }
}
