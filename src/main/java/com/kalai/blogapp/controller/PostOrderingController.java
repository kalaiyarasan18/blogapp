package com.kalai.blogapp.controller;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.paging.PostServiceImp;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.service.PostService;
import com.kalai.blogapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.kalai.blogapp.controller.PostController.globalPost;

@Controller
public class PostOrderingController {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostServiceImp postServiceImp;
    @Autowired
    TagService tagService;

    @PostMapping(value = "sortPost")
    public String sortPost(Model model, @RequestParam("sortBy") String sortBy) {
        System.out.println("Inside the searpost by: " + sortBy);
        System.out.println("Global list is :" + globalPost.size() + "\n");
        List<Post> sortedPost = new ArrayList<>();
        if (sortBy.equals("PublishedTimeAsc")) {
            Collections.sort(globalPost);
        } else {
            Collections.sort(globalPost, Collections.reverseOrder());
        }
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", globalPost);
        return "listofpost";
    }

    @GetMapping(value = "search")
    public String searchPost(Model model, @RequestParam("search") String keyword) {
        List<Post> searchResult = postServiceImp.searchAllBySearch(keyword);
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", searchResult);
        model.addAttribute("noOfResult", searchResult.size());
        return "listofpost";
    }

}
