package com.kalai.blogapp.controller;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.paging.PostServiceImp;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.service.PostService;
import com.kalai.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.kalai.blogapp.paging.PostServiceImp.globalPost;

@Controller
public class PostOrderingController {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostServiceImp postServiceImp;
    @Autowired
    TagService tagService;

    @PostMapping(value = "sortPost")
    public String sortPost(Model model, @RequestParam("sortBy") String sortBy) {
        List<Post> sortedPost = postService.sortPostBy(sortBy);
        model.addAttribute("posts", sortedPost);
        return "listofpost";
    }

    @GetMapping(value = "search")
    public String searchPost(Model model, @RequestParam("search") String keyword) {
        List<Post> searchResult = postService.search(keyword, globalPost);
        model.addAttribute("posts", searchResult);
        return "listofpost";
    }

}
