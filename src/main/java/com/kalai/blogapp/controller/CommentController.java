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

    @PostMapping(value = "comment")
    public String commentHandler(
            @RequestParam("name") String name, @RequestParam("email") String email,
            @RequestParam("content") String content, @RequestParam("hiddenbtn") long id
    ) {
        Comment comment = new Comment();
        comment.setCommenterName(name);
        comment.setCommenterEmail(email);
        comment.setCommentContent(content);
        comment.setCommentedPostId(id);
        commentService.handleSave(comment);
        return "redirect:/readMore/" + id;
    }

    @GetMapping(value = "deleteComment/{commentId}")
    public String deleteComment(Model model, @PathVariable("commentId") Long id) {
        Comment c = commentService.getPostById(id);
        commentService.deleteComment(id);
        return "redirect:/readMore/" + c.getCommentedPostId();
    }

    @GetMapping(value = "updateComment/{commentId}")
    public String updateComment(Model model, @PathVariable("commentId") long id,
                                @ModelAttribute("commentData") Comment comment) {
        Comment beforeUpdateComment = commentRepository.findById(id);
        long postId = beforeUpdateComment.getCommentedPostId();
        Post postToShow = postService.getPostById(postId);
        model.addAttribute("commentData", beforeUpdateComment);
        model.addAttribute("postOfGivenId", postToShow);
        return "updatecomment";
    }

    @PostMapping(value = "edit")
    public String editComment(
            @RequestParam("name") String commenterName, @RequestParam("email") String commenterEmail,
            @RequestParam("content") String commentContent, @RequestParam("hidden") long commentId, Model model) {

        Comment commentToUpdate = commentRepository.findById(commentId);
        System.out.print("Inside that postmethod:" + commentToUpdate);
        commentToUpdate.setCommenterName(commenterName);
        commentToUpdate.setCommenterEmail(commenterEmail);
        commentToUpdate.setCommentContent(commentContent);
        commentRepository.save(commentToUpdate);
        return "redirect:/readMore/" + commentToUpdate.getCommentedPostId();
    }

}
