package com.kalai.App.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    @PostMapping(value = "comment")
    public String handleComment(Model model, @RequestParam("commentBox") String comment){
        return "";
    }
}
