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
        if (query != null) {
            /*List<Post> posts = new ArrayList<Post>();
            posts.addAll(postServiceImp.processQuery(postService.buildQueryForFilter(query)));
            System.out.println("author posts = " + posts.size());
            List<String> authors = postServiceImp.getAllAuthors();
            List<String> tags = postServiceImp.getAllTags();
            model.addAttribute("authors", authors);
            model.addAttribute("tags", tags);
            model.addAttribute("posts", posts);
            return "listofpost";*/
            stringBuilder.delete(0,stringBuilder.length());
            stringBuilder.append(postService.buildQueryForFilter(query));
        }
        if (startdate != "" && enddate != "") {
            stringBuilder.append("AND publishedAt between '" + startdate + "' AND '" + enddate + "' ");
        }
        if (sortby != "") {
            if (sortby.equals("desc")) {
                stringBuilder.append("order by p.publishedAt desc ");
            } else {
                stringBuilder.append("order by publishedAt asc ");
            }
        }
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", postServiceImp.processQuery(stringBuilder.toString()));
        return "listofpost";
    }

    @GetMapping(value = "/search")
    public String filterOnSearch(Model model, @RequestParam("search") String search) {
        String sb = "";
        if (search != null) {
            sb = "select p from Post p where p.title like '%" + search + "%' or p.content like '%" + search + "%' or p.author like '%" + search + "%'" +
                    " or id in (select distinct pt.postId from PostTag pt,Tag t where pt.tagId=t.id and t.name like '%" + search + "%')";
        }
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("prev", search);
        model.addAttribute("posts", postServiceImp.processQuery(sb));
        return "filterPost";
    }

    @PostMapping("insearch")
    public String filterInSearch(@RequestParam(value = "sortBy", required = false) String sortby,
                                 @RequestParam(value = "author", required = false) String query,
                                 @RequestParam(value = "startdate", required = false) String startdate,
                                 @RequestParam(value = "enddate", required = false) String enddate,
                                 @RequestParam(value = "keyword") String search, Model model) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select p from Post p where p in (");
        stringBuilder.append(postService.buildQueryforSearch(search)).append(") ");
        if (query != null) {
            List<Post> posts = new ArrayList();
            String queries[] = query.split(",");
            for (String keyword : queries) {
                posts.addAll(postRepository.findAllBySearch(keyword));
            }
            model.addAttribute("posts", posts);
        }
        if (startdate != "" && enddate != "") {
            stringBuilder.append("AND publishedAt between '" + startdate + "' AND '" + enddate + "' ");
        }
        if (sortby != null) {
            if (sortby.equals("desc")) {
                stringBuilder.append("order by p.publishedAt desc ");
            } else
                stringBuilder.append("order by publishedAt asc ");
        }
        List<String> authors = postServiceImp.getAllAuthors();
        List<String> tags = postServiceImp.getAllTags();
        model.addAttribute("authors", authors);
        model.addAttribute("tags", tags);
        model.addAttribute("posts", postServiceImp.processQuery(stringBuilder.toString()));
        return "listofpost";
    }
}

