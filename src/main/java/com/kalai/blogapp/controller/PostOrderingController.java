package com.kalai.blogapp.controller;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostOrderingController {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @PostMapping(value = "sortPost")
    public String sortPost(Model model, @RequestParam("sortBy") String sortBy) {
        List<Post> sortedPost = postService.sortPostBy(sortBy);
        model.addAttribute("posts", sortedPost);
        return "listofpost";
    }

    @GetMapping(value = "search")
    public String searchPost(Model model, @RequestParam("searchField") String keyword) {
        List<Post> searchResult = postService.search(keyword);
        model.addAttribute("posts", searchResult);
        return "listofpost";
    }

}
