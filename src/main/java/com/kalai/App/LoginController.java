package com.kalai.App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kalai.App.Entity.Posts;
import com.kalai.App.Repository.PostsRepository;
import com.kalai.App.Service.PostService;

import java.util.List;

@Controller
public class LoginController {
	
	@Autowired
	PostsRepository postsrepository;
	@Autowired
	PostService postService;
	
	@GetMapping(value="create")
	public String getBlogPost(){
		return "creater";
	}

	/*@PostMapping(value="createpost")
	public String postBlogPost(@ModelAttribute("posts") Posts posts,
			@RequestParam("title") String title,
			@RequestParam("excerpt")String excerpt,
			@RequestParam("content") String content,
			@RequestParam("author")String author) {
		if(!postService.savePost(title,excerpt,content,author)==true) {
			return "Error in database";
		}
		System.out.print(postService.getAllPosts());
		return "listofpost";
	}*/
	@PostMapping(value="createpost")
	public String listPosts(Model model){
		List<Posts> posts=postService.getAllPosts();
		model.addAttribute("posts",posts);
		return "listofpost";
	}
	
}
