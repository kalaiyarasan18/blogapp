package com.kalai.App.controller;

import com.kalai.App.repository.CommentRepository;
import com.kalai.App.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping(value = "comment")
    public String handleComment(Model model, @RequestParam("commentBox") String comment,
                                @RequestParam("hiddenBtn") Long postId){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDateAndTime = new Date();
        commentService.saveComment(comment,postId,currentDateAndTime,currentDateAndTime);
        return "/readMore/?postId="+postId;
    }
}
