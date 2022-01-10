package com.kalai.blogapp.controller;

import com.kalai.blogapp.entity.Comment;
import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.repository.CommentRepository;
import com.kalai.blogapp.service.CommentService;
import com.kalai.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping(value = "/comment")
    public String saveComment(
            @RequestParam("name") String name, @RequestParam("email") String email,
            @RequestParam("content") String content, @RequestParam("hiddenbtn") long id
    ) {
        System.out.println("reached comment ");
        Comment comment = new Comment();
        comment.setName(name);
        comment.setEmail(email);
        comment.setContent(content);
        commentService.save(comment,id);
        return "redirect:/readMore/" + id;
    }

    @GetMapping(value = "deleteComment/{commentId}")
    public String deleteComment(Model model, @PathVariable("commentId") Long id) {
        Comment comment = commentService.getPostById(id);
        long postId=comment.getPost().getId();
        commentService.deleteComment(id);
        return "redirect:/readMore/" + postId;
    }

    @GetMapping(value = "updateComment/{commentId}")
    public String updateComment(Model model, @PathVariable("commentId") long id,
                                @ModelAttribute("commentData") Comment comment) {
        Comment prevComment = commentRepository.findById(id);
        long postId=comment.getPost().getId();
        Post postToShow = postService.getPostById(postId);
        model.addAttribute("commentData", prevComment);
        model.addAttribute("post", postToShow);
        return "updatecomment";
    }

    @PostMapping(value = "edit")
    public String editComment(
            @RequestParam("name") String commenterName, @RequestParam("email") String commenterEmail,
            @RequestParam("content") String commentContent, @RequestParam("hidden") long commentId, Model model) {

        Comment commentToUpdate = commentRepository.findById(commentId);
        commentToUpdate.setName(commenterName);
        commentToUpdate.setEmail(commenterEmail);
        commentToUpdate.setContent(commentContent);
        commentRepository.save(commentToUpdate);
        long postId=commentToUpdate.getPost().getId();
        return "redirect:/readMore/" + postId;
    }

}
