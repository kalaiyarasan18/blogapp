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
import java.util.List;

@Controller
public class PostOrderingController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostServiceImp postServiceImp;

    @Autowired
    private TagService tagService;


    @PostMapping(value = "filterpost")
    public String filterData(@RequestParam(value = "sortBy", required = false) String sortby,
                             @RequestParam(value = "author", required = false) String query,
                             @RequestParam(value = "startdate", required = false) String startdate,
                             @RequestParam(value = "enddate", required = false) String enddate,
                             @RequestParam(value = "search", required = false) String search, Model model
    ) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select p from Post p where p.isPublished=true ");
        if (sortby != null) {
            if (sortby.equals("desc")) {
                stringBuilder.append("order by p.publishedAt desc ");
            } else
                stringBuilder.append("order by publishedAt asc ");
            model.addAttribute("posts", postServiceImp.processQuery(stringBuilder.toString()));
        } else if (query != null) {
            List<Post> posts=new ArrayList();
            String queries[]=query.split(",");
            for(String keyword:queries){
                posts.addAll(postRepository.findAllBySearch(keyword));
            }
            model.addAttribute("posts", posts);
        } else if (search != null) {
            String sb = "select p from Post p where p.title like '%" + search + "%' or p.content like '%" + search + "%' or p.author like '%" + search + "%'" +
                    " or id in (select distinct pt.postId from PostTag pt,Tag t where pt.tagId=t.id and t.name like '%" + search + "%')";
            System.out.println("reached filersearch");
            List<String> authors = postServiceImp.getAllAuthors();
            List<String> tags = postServiceImp.getAllTags();
            model.addAttribute("authors", authors);
            model.addAttribute("tags", tags);
            model.addAttribute("prev",search);
            model.addAttribute("posts", postServiceImp.processQuery(sb));
            return "filterpost";
        } else if (startdate != null && enddate != null) {
            stringBuilder.append("AND publishedAt between '" + startdate + "' AND '" + enddate + "' ");
            model.addAttribute("posts", postServiceImp.processQuery(stringBuilder.toString()));
        }
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        return "listofpost";
    }

    @GetMapping(value = "filterOnSearch")
    public String filterOnSearch(Model model, @RequestParam("prev") String prevQuery,
                                 @RequestParam("author")String author) {
        System.out.println("prevQuery = " + prevQuery);
        String query="select p from Post p where p.id in ".concat(prevQuery);
        System.out.println("new query = " + query);
        model.addAttribute("posts",postServiceImp.processQuery(prevQuery));
        return "filterpost";
    }



}
