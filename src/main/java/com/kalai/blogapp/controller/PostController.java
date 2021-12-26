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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {
    public static List<Post> globalPost=new ArrayList<>();
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
                         @RequestParam(value = "tag", required = false) String tag,
                         @RequestParam(value = "date", required = false) String date,
                         @RequestParam(value = "start", defaultValue = "0", required = true) int start,
                         @RequestParam(value = "limit", defaultValue = "5", required = true) int limit
    ) {

         if (author == null && tag == null) {
             System.out.println("Both are null,So size is 5\n");
            List<Post> allPost = postServiceImp.findAll(start, limit);
             globalPost.clear();
             globalPost.addAll(allPost);
        }else if(author==null&&tag!=null){
             List<Post> postsForTags=postServiceImp.handleFilter(author);
             globalPost.clear();
             globalPost.addAll(postsForTags);
         } else if(tag==null&&author!=null){
             List<Post> postsForTags=postServiceImp.handleFilterForTag(tag);
             globalPost.clear();
             globalPost.addAll(postsForTags);
         }else {
             System.out.println("printing globalPost before handlefilter:"+globalPost.size());
            List<Post> filterdPost = postServiceImp.handleFilter(author);
            filterdPost.addAll(postServiceImp.handleFilterForTag(tag));
            globalPost.clear();
            globalPost.addAll(filterdPost);
             System.out.println("printing globalPost inside Not auth null:"+globalPost.size());
        }
        System.out.println("printing globalPost:"+globalPost.size());
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("posts", globalPost);
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        return "listofpost";
    }

    @GetMapping(value = "/", params = {"start", "limit"})
    public String gotoPage(@RequestParam("start") int offset, @RequestParam("limit") int limit, Model model) {
        List<Post> posts = postServiceImp.findAll(offset, limit);
        globalPost.clear();
        globalPost.addAll(posts);
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
        System.out.println("printing globalPost inside list:"+globalPost.size());
        model.addAttribute("posts", globalPost);
        return "listofpost";
    }

    @PostMapping(value = "/createpost")
    public String listPosts(@RequestParam("title") String title,
                            @RequestParam("excerpt") String excerpt,
                            @RequestParam("content") String content,
                            @RequestParam("author") String author,
                            @RequestParam("tag") String tag, Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Post post = new Post(title, excerpt, content, author, date);
        post.setPostTitle(title);
        post.setPostExcerpt(excerpt);
        post.setPostContent(content);
        post.setPostAuthor(author);
        post.setPostCreatedAt(date);
        post.setPostUpdatedAt(date);
        post.setPostPublishedAt(date);
        post.setPostIsPublished(true);
        Post savedPost = postsrepository.save(post);
        List<Post> posts = postService.getAllPosts();
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("posts", globalPost);
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
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
    public String updatePostContent(@ModelAttribute("posts") Post posts, Model model,@RequestParam("tag")String tag) {
        postService.postToUpdate(posts);
        tagService.mapTagToPost(tag,posts.getPostId());
        model.addAttribute("posts", posts);
        return "redirect:/list";
    }

    @GetMapping(value = "deletePost/{postId}")
    public String deletePost(Model model, @PathVariable("postId") long id) {
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
}
