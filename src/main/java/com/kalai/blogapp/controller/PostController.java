package com.kalai.blogapp.controller;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.paging.PostServiceImp;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.service.CommentService;
import com.kalai.blogapp.service.PostService;
import com.kalai.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@Controller
public class PostController {
    public static List<Post> globalPost = new ArrayList<>();
    public static int limit = 10;
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
    public String goHome(Model model,@RequestParam(value = "start", defaultValue = "0", required = true) int start
    ) {
        model.addAttribute("pageno", start);
        List<Post> posts=new ArrayList<>();
        posts.addAll(postServiceImp.findAllPages(start, limit).getContent());
        model.addAttribute("posts", posts);
        model.addAttribute("noOfResult", posts.size());
        model.addAttribute("totalpages", postServiceImp.findAllPages(start, limit).getTotalPages());
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("tags", tags);
        return "listofpost";
    }

    @GetMapping("/reset")
    public String reset(Model model) {
        return "redirect:/";
    }

    @GetMapping(value = "new")
    public String createPost(Model model) {
        return "creater";
    }

    @GetMapping(value = "list")
    public String listPostDirectly(Model model) {
        List<Post> posts = postsrepository.findAll();
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", posts);
        return "listofpost";
    }

    @PostMapping(value = "/createpost")
    public String listPosts(@RequestParam("title") String title,
                            @RequestParam("excerpt") String excerpt,
                            @RequestParam("content") String content,
                            @RequestParam("author") String author,
                            @RequestParam("tag") String tag, Model model) {
        Date date = new Date();
        Post post = new Post();
        post.setTitle(title);
        post.setExcerpt(excerpt);
        post.setContent(content);
        post.setAuthor(author);
        post.setCreatedAt(date);
        post.setUpdatedAt(date);
        post.setPublishedAt(date);
        post.setPublished(true);
        postService.savePost(post, tag);
        List<Post> posts = postService.getAllPosts();
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("posts", globalPost);
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        return "redirect:/listofpost";
    }

    @GetMapping(value = "updatePost/{postId}")
    public String updatePost(Model model, @PathVariable("postId") long id) {
        Post postForUpdate = postService.getPostById(id);
        String tags = tagService.tagByPostId(id).toString();
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tag = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", postForUpdate);
        return "updatepost";
    }

    @PostMapping(value = "updatepost")
    public String updatePostContent(@ModelAttribute("posts") Post posts, Model model) {
        postService.postToUpdate(posts);
        model.addAttribute("posts", posts);
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        return "redirect:/list";
    }

    @GetMapping(value = "deletePost/{postId}")
    public String deletePost(Model model, @PathVariable("postId") long id) {
        postService.deletePostById(id);
        postServiceImp.deleteTagByPostId(id);
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

    @GetMapping(value = "login")
    public String login(){
        return "login";
    }


}
