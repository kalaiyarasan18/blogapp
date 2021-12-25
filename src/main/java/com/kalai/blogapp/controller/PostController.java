package com.kalai.blogapp.controller;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.paging.PostServiceImp;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.service.CommentService;
import com.kalai.blogapp.service.PostService;
import com.kalai.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    TagService tagService;
    @Autowired
    PostServiceImp postServiceImp;

    @RequestMapping(value = "/")
    public String goHome() {
        return "redirect:/list";
    }

    @GetMapping(value = "/", params = {"start", "limit"})
    public String gotoPage(@RequestParam("start") int offset, @RequestParam("limit") int limit, Model model) {
        List<Post> posts = postServiceImp.findAll(offset, limit);
        model.addAttribute("posts", posts);
        return "listofpost";
    }

    @GetMapping(value = "new")
    public String createPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "creater";
    }

    @GetMapping(value = "list")
    public String listPostDirectly(Model model) {
        /*List<Post> posts = postService.getAllPosts();*/
        List<Post> posts = postServiceImp.findAll(0, 5);
        model.addAttribute("posts", posts);
        return "listofpost";
    }

    @PostMapping(value = "/createpost")
    public String listPosts(@RequestParam("title") String title,
                            @RequestParam("excerpt") String excerpt,
                            @RequestParam("content") String content,
                            @RequestParam("author") String author,
                            @RequestParam("tag") String tag, Model model) {
        Post post = new Post();
        post.setPostTitle(title);
        post.setPostExcerpt(excerpt);
        post.setPostContent(content);
        post.setPostAuthor(author);
        post.setPostCreatedAt(new Date());
        post.setPostUpdatedAt(new Date());
        post.setPostPublishedAt(new Date());
        Post savedPost = postsrepository.save(post);
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        tagService.mapTagToPost(tag, post.getPostId());
        return "listofpost";
    }

    @GetMapping(value = "updatePost/{postId}")
    public String updatePost(Model model, @PathVariable("postId") long id) {
        Post postForUpdate = postService.getPostById(id);
        String tags = tagService.tagByPostId(id);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", postForUpdate);
        return "updatepost";
    }

    @PostMapping(value = "updatepost")
    public String updatePostContent(@ModelAttribute("posts") Post posts, Model model) {
        postService.postToUpdate(posts);
        model.addAttribute("posts", posts);
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
        String tags = tagService.tagByPostId(id);
        model.addAttribute("tags", tags);
        model.addAttribute("postOfGivenId", postOfGivenId);
        model.addAttribute("comments", commentService.commentById(id));
        return "fullblog";
    }
}
