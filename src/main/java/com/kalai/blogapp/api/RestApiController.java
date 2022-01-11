package com.kalai.blogapp.api;

import com.kalai.blogapp.entity.*;
import com.kalai.blogapp.exception.PostException;
import com.kalai.blogapp.jwt.JwtTokenUtil;
import com.kalai.blogapp.paging.PostServiceImp;
import com.kalai.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {
    @Autowired
    PostService postService;
    @Autowired
    PostServiceImp postServiceImp;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil jwUtil;

    @GetMapping("/post")
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @PostMapping(value = "/authenticate")
    public String authenticateUser(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username and password");
        }
        String jwt = jwUtil.generateToken(authRequest.getUsername());
        AuthenticationResponse response = new AuthenticationResponse(jwt);
        String token = response.getJwt();
        return token;
    }

    @RequestMapping(value = "/post/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean updatePost(@RequestBody PostDto post) {
        System.out.println(post);
        return true;
    }

    @DeleteMapping("/post/{postId}")
    public boolean deletePost(@PathVariable("postId") long postId) {
        postService.deletePostById(postId);
        return true;
    }

    @GetMapping("/post/{id}")
    public Post postById(@PathVariable("id") long id) {
        if(id>100)
            throw  new PostException("post not found..");
        else
            return postService.getPostById(id);
    }

    @GetMapping(value = "/search")
    public List<Post> searchPost(@RequestParam("search") String search) {
        String sb = "";
        if (search != null) {
            sb = "select p from Post p where p.title like '%" + search + "%' or p.content like '%" + search + "%' or p.author like '%" + search + "%'" + " or id in (select distinct pt.postId from PostTag pt,Tag t where pt.tagId=t.id and t.name like '%" + search + "%')";
        }
        return postServiceImp.processQuery(sb);
    }

    @GetMapping(value = "filter")
    public List<Post> filterData(@RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "author", required = false) String query, @RequestParam(value = "startdate", required = false) String startdate, @RequestParam(value = "enddate", required = false) String enddate, @RequestParam(value = "search", required = false) String search) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select p from Post p where p.isPublished=true ");
        if (query != null) {
            stringBuilder.delete(0, stringBuilder.length());
            stringBuilder.append(postService.buildQueryForFilter(query));
        }
        if (startdate != null && enddate != null) {
            stringBuilder.append("AND publishedAt between '" + startdate + "' AND '" + enddate + "' ");
        }
        if (sortBy != null) {
            if (sortBy.equals("desc")) {
                stringBuilder.append("order by p.publishedAt desc ");
            } else {
                stringBuilder.append("order by p.publishedAt asc ");
            }
        }
        return postServiceImp.processQuery(stringBuilder.toString());
    }
}
