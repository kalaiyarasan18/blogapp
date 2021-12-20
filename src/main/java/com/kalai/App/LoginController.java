package com.kalai.App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
	@GetMapping(value="list")
	public String listPostDirectly(Model model){
		List<Posts> posts=postService.getAllPosts();
		model.addAttribute("posts",posts);
		return "listofpost";
	}
	@PostMapping(value="createpost")
	public String listPosts(Model model,
		@RequestParam("title") String title,
		@RequestParam("excerpt")String excerpt,
		@RequestParam("content") String content,
		@RequestParam("author")String author) {
			if(!postService.savePost(title,excerpt,content,author)==true) {
				return "Error in database";
			}
		List<Posts> posts=postService.getAllPosts();
		model.addAttribute("posts",posts);
		return "listofpost";
	}
	@GetMapping(value="updatePost")
	@ResponseBody
	public String updatePost(){
		return "Readched update button clicked";
	}
	@GetMapping(value="deletePost")
	@ResponseBody
	public String deletePost(){
		return "Readched delete button clicked";
	}
	@GetMapping(value="readMore")
	@ResponseBody
	public String readMore(){
		return "Readched readmore button clicked";
	}
}
