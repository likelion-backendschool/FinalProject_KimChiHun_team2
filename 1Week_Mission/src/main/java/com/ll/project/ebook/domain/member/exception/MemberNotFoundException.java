package com.ll.project.ebook.domain.member.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String s) {
        super(s);
    }
}
