package com.ll.project.ebook.domain.post.service;

import com.ll.project.ebook.domain.post.dto.PostListDto;
import com.ll.project.ebook.domain.post.entity.Post;
import com.ll.project.ebook.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    @Transactional(readOnly = true)
    public List<PostListDto> findAll() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        if(posts == null){
            return null;
        }
        return posts.stream()
                .map(PostListDto::new)
                .collect(Collectors.toList());
    }
}
