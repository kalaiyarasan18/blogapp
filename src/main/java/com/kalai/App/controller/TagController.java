package com.kalai.App.controller;

import com.kalai.App.entity.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TagController {

    @GetMapping(value = "/createtag")
    public String tagCreate(@ModelAttribute("tag") Tag tag, Model model){

        return "";
    }
}
