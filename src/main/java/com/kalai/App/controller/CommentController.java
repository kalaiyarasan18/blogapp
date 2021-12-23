package com.kalai.App.controller;

import com.kalai.App.entity.Comment;
import com.kalai.App.entity.Post;
import com.kalai.App.repository.CommentRepository;
import com.kalai.App.service.CommentService;
import com.kalai.App.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    PostService postService;

    @PostMapping(value = "comment")
    public String commentHandler(
            @RequestParam("name")String name,@RequestParam("email")String email,
            @RequestParam("content")String content,@RequestParam("hiddenbtn")long id
    ){
        Comment comment=new Comment();
        comment.setCommenterName(name);
        comment.setCommenterEmail(email);
        comment.setCommentContent(content);
        comment.setCommentedPostId(id);
        commentService.handleSave(comment);
        return "redirect:/readMore/"+id;
    }
    @GetMapping(value = "deleteComment/{commentId}")
    public String deleteComment(Model model,@PathVariable("commentId")Long id){
        Comment c=commentService.getPostById(id);
        commentService.deleteComment(id);
        return "redirect:/readMore/"+c.getCommentedPostId();
    }

    @GetMapping(value = "updateComment/{commentId}")
    public String updateComment(Model model,@PathVariable("commentId")Long id,
                                @ModelAttribute("commentData")Comment comment){
        commentService.update(comment);
        long postId=commentService.getIdForPost(id);
        Comment commentToUpdate=commentService.getPostById(id);
        Post postToShow=postService.getPostById(postId);
        model.addAttribute("commentData",commentToUpdate);
        model.addAttribute("postOfGivenId",postToShow);
        return "updatecomment";
    }

    @PostMapping(value="/editComment")
    @ResponseBody
    public String ediComment(
            @RequestParam("name")String commenterName,@RequestParam("email")String commenterEmail,
            @RequestParam("content")String commentContent,@RequestParam("hidden")long commentId,Model model,
            @RequestParam("postId")long postId){
        Comment comment=new Comment();
        comment.setCommentId(commentId);
        comment.setCommenterName(commenterName);
        comment.setCommenterEmail(commenterEmail);
        comment.setCommentContent(commentContent);
        commentService.update(comment);
        return "redirect:/readMore/"+postId;
    }


}
