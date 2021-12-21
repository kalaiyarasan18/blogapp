package com.kalai.App.controller;

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
public class LoginController {
    @Autowired
    PostRepository postsrepository;
    @Autowired
    PostService postService;

    @RequestMapping(value = "/")
    public String goHome() {
        return "home";
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
        model.addAttribute("post",postForUpdate);
        return "updatepost";
    }

   /* @PostMapping(value="update")
    public String updatePostContent(@RequestParam("author")String postAuthor,@RequestParam("title")String postTitle,
                                    @RequestParam("excerpt") String postExcerpt,
                                    @RequestParam("content") String postContent,@RequestParam("hidden") long id,Model model){
        postService.updatePost(id,postTitle,postExcerpt,postContent,postAuthor,new Date());
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "listofpost";
    }*/
    @PostMapping(value="update")
    public String updatePost(@ModelAttribute("updatedPost")Post receivedPost,Model model){
        postService.updatePost(receivedPost);
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
        return "fullblog";
    }
}
