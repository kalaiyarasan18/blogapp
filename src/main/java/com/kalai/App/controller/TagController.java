package com.kalai.App.controller;

import com.kalai.App.entity.Tag;
import com.kalai.App.repository.TagRepository;
import com.kalai.App.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TagController {

    @Autowired
    TagService tagService;
    @Autowired
    TagRepository tagRepository;


    @GetMapping(value = "/createtag")
    public String showTagForm(){
        return "tagcreate";
    }
    @PostMapping(value = "/createtag")
    @ResponseBody
    public String tagCreate(@RequestParam("tagname")String name, Model model){
        tagService.create(name);
        return "Tag Created Successfully!";
    }
}
