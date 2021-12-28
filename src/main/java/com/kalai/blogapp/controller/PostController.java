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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {
    public static List<Post> globalPost = new ArrayList<>();
    public static List<Post> globalFilter=new ArrayList<>();
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
    public String goHome(Model model, @RequestParam(value = "author", required = false) String author,
                         @RequestParam(value = "start", defaultValue = "0", required = true) int start,
                         @RequestParam(value = "limit", defaultValue = "10", required = true) int limit
    ) {
        List<Post> posts=new ArrayList<>();
        if (author == null) {
            List<Post> allPost = postServiceImp.findAll(start, limit);
            posts.addAll(allPost);
        } else {
          posts.addAll(postServiceImp.searchAllBySearch(author));
        }
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("posts",posts);
        model.addAttribute("noOfResult",posts.size());
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        return "listofpost";
    }

    @GetMapping(value = "/", params = {"start", "limit"})
    public String gotoPage(@RequestParam("start") int offset, @RequestParam("limit") int limit, Model model) {
        List<Post> posts = postServiceImp.findAll(offset, limit);
        globalPost.clear();
        globalPost.addAll(posts);
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", globalPost);
        return "listofpost";
    }

    @GetMapping(value = "new")
    public String createPost(Model model) {
        return "creater";
    }

    @GetMapping(value = "list")
    public String listPostDirectly(Model model) {
        List<Post> posts = postsrepository.findAll();
        globalPost.clear();
        globalPost.addAll(posts);
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", globalPost);
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
        post.setPostTitle(title);
        post.setPostExcerpt(excerpt);
        post.setPostContent(content);
        post.setPostAuthor(author);
        post.setPostCreatedAt(date);
        post.setPostUpdatedAt(date);
        post.setPostPublishedAt(date);
        post.setPostIsPublished(true);
        postService.savePost(post,tag);
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
        String tags = tagService.tagByPostId(id);
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tag = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tag);
        model.addAttribute("posts", postForUpdate);
        return "updatepost";
    }

    @PostMapping(value = "updatepost")
    public String updatePostContent(@ModelAttribute("posts") Post posts, Model model) {
        postService.postToUpdate(posts);
        model.addAttribute("posts", posts);
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("posts", globalPost);
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

    @RequestMapping(value = "/filterBy")
    public String filterBy(@RequestParam(value = "startdate", required = false) String startdate,
                           @RequestParam(value = "enddate", required = false) String enddate,
                           Model model) throws ParseException {
        List<Post> postBetweenDate=new ArrayList<>();
        postBetweenDate=postService.getPostByDate(startdate,enddate);
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", postBetweenDate);
        return "listofpost";
    }
}
