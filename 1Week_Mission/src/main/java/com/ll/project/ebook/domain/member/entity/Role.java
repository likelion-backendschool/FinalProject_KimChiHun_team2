package com.ll.project.ebook.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    MEMBER(3),
    AUTHOR(4),
    ADMIN(7);
    private final int authLevel;

    public static String stringValue(int authLevel){
        switch (authLevel){
            case 3:
                return "MEMBER";
            case 4:
                return "AUTHOR";
            case 7:
                return "ADMIN";
            default:
                throw new AssertionError("Unknown value: " + authLevel);
        }
    }
}
