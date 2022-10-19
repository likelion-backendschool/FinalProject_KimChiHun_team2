package com.ll.project.ebook.domain.post.controller;

import com.ll.project.ebook.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String showList(Model model){
        model.addAttribute("posts", postService.findAll());
        return "post/list";
    }
}
