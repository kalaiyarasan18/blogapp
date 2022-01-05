package com.kalai.blogapp.api;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {
    @Autowired
    PostService postService;

    @GetMapping("/all")
    public @ResponseBody List<Post> testApi(){
        return postService.getAllPosts();
    }

    @GetMapping("/post/{id}")
    public Post postById(@PathVariable("id")long id){
        return postService.getPostById(id);
    }
}
