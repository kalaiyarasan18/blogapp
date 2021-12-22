package com.kalai.App.controller;

import com.kalai.App.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kalai.App.entity.Post;
import com.kalai.App.repository.PostRepository;
import com.kalai.App.service.PostService;

import java.util.Date;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    PostRepository postsrepository;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/")
    public String goHome() {
        return "redirect:/list";
    }

    @GetMapping(value = "new")
    public String createPost(){
        return "creater";
    }

    @GetMapping(value = "list")
    public String listPostDirectly(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "listofpost";
    }

    @PostMapping(value = "createpost")
    public String listPosts(Model model,
                            @RequestParam("title") String title,
                            @RequestParam("excerpt") String excerpt,
                            @RequestParam("content") String content,
                            @RequestParam("author") String author) {
        if (!postService.savePost(title, excerpt, content, author) == true) {
            return "Error in database";
        }
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "listofpost";
    }

    @GetMapping(value = "updatePost/{postId}")
    public String updatePost(Model model, @PathVariable("postId") long id) {
        Post postForUpdate=postService.getPostById(id);
        model.addAttribute("posts",postForUpdate);
        return "updatepost";
    }

    @PostMapping(value="updatepost")
    public String updatePostContent(@ModelAttribute("posts")Post posts,Model model){
        postService.postToUpdate(posts);
        model.addAttribute("posts",posts);
        return "redirect:/list";
    }
    @GetMapping(value = "deletePost/{postId}")
    public String deletePost(Model model, @PathVariable("postId") long id) {
        postService.deletePostById(id);
        return "redirect:/list";
    }

    @GetMapping(value = "readMore/{postId}")
    public String readMore(@PathVariable("postId") long id, Model model) {
        Post postOfGivenId = postService.getPostById(id);
        model.addAttribute("postOfGivenId", postOfGivenId);
        model.addAttribute("comments",commentService.commentById(id));
        return "fullblog";
    }
}
