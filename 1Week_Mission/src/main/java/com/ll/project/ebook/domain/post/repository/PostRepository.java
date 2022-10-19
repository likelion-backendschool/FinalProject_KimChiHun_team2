package com.ll.project.ebook.domain.post.repository;

import com.ll.project.ebook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Member, Long> {
}
