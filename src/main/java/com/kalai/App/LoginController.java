package com.kalai.App;

import java.net.http.HttpRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kalai.App.Entity.Posts;

@Controller
public class LoginController {
	
	@GetMapping(value="create")
	public String getBlogPost(){
		return "creater";
	}
	@PostMapping(value="createpost")
	public String postBlogPost(@ModelAttribute("posts") Posts posts,
			@RequestParam("title") String title,
			@RequestParam("excerpt")String excerpt,
			@RequestParam("content") String content,
			@RequestParam("author")String author) {
		
		Posts post=new Posts(title,excerpt,content,author);
		System.out.println(post);
		return "listofpost";
	}
	
}
