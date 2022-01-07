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

    @GetMapping("/post")
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @PutMapping("/post")
    public boolean updatePost(@RequestBody Post post) {
        String tag = post.getTags().toString();
        postService.savePost(post, tag);
        return true;
    }

    @DeleteMapping("/post/{postId}")
    public boolean deletePost(@PathVariable("postId") long postId) {
        postService.deletePostById(postId);
        return true;
    }

    @GetMapping("/post/{id}")
    public Post postById(@PathVariable("id") long id) {
        return postService.getPostById(id);
    }
}
