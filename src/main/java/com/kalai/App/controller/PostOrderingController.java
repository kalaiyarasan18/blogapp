package com.kalai.App.controller;

import com.kalai.App.entity.Post;
import com.kalai.App.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostOrderingController {

    @Autowired
    PostService postService;

    @PostMapping(value="sortPost")
    public String sortPost(Model model, @RequestParam("sortBy") String sortBy){
       List<Post> sortedPost=postService.sortPostBy(sortBy);
       model.addAttribute("posts",sortedPost);
        return "listofpost";
    }

    @PostMapping(value="filterPost")
    public String filterPost(Model model,@RequestParam("filterBy")String filterBy){

        return "";
    }
    @PostMapping(value="searchPost")
    public String searchPost(Model model){
        return "listofpost";
    }
}
