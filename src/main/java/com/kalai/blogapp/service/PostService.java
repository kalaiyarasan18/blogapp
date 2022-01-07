package com.kalai.blogapp.service;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.entity.Tag;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PostService {
    @Autowired
    PostRepository postsrepository;
    @Autowired
    TagRepository tagRepository;

    public void savePost(Post post, String tag) {
        Date date = new Date();
        String[] tags = tag.split(",");
        for (String eachTag : tags) {
            if (tagRepository.findByName(eachTag.toLowerCase().trim()) == null) {
                Tag tag2 = new Tag();
                tag2.setName(eachTag.toLowerCase().trim());
                tag2.setUpdatedAt(new Date());
                if (tag2.getCreatedAt() == null) {
                    tag2.setCreatedAt(new Date());
                }
                post.getTags().add(tag2);
                tag2.getPosts().add(post);
            } else {
                Tag tag3 = tagRepository.findByName(eachTag.toLowerCase().trim());
                tag3.setName(eachTag.toLowerCase().trim());
                post.getTags().add(tag3);
            }
        }
        postsrepository.save(post);
    }

    public List<Post> getAllPosts() {
        List<Post> allPosts = postsrepository.findAll();
        return allPosts;
    }

    public Post getPostById(long id) {
        Post post = postsrepository.findById(id);
        return post;
    }

    public void deletePostById(long id) {
        postsrepository.deleteById(id);
    }

    public void postToUpdate(Post posts) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        postsrepository.updatePost(posts.getId(), posts.getTitle(),
                posts.getExcerpt(), posts.getContent(),
                posts.getAuthor(), date);
    }

    public List<Post> getPostByDate(String startDate, String endDate) throws ParseException {
        List<Post> postBetweenDate = new ArrayList<>();
        List<Post> allPostFromDb = new ArrayList<>();
        allPostFromDb.addAll(postsrepository.findAll());
        for (Post eachPost : allPostFromDb) {
            String date = eachPost.getPublishedAt().toString();
            if (startDate.compareTo(date) <= 0 && endDate.compareTo(date) >= 0) {
                postBetweenDate.add(eachPost);
            }
        }
        return postBetweenDate;
    }

    public String buildQueryforSearch(String keyword){
        String sb = "select p from Post p where p.title like '%" + keyword + "%' or p.content like '%" + keyword + "%' or p.author like '%" + keyword + "%'" +
                " or id in (select distinct pt.postId from PostTag pt,Tag t where pt.tagId=t.id and t.name like '%" + keyword + "%')";
        return sb;
    }

    public String buildQueryForFilter(String keyword){
        String keywords[]=keyword.split(",");
        StringBuilder sb=new StringBuilder();
        sb.append("select distinct p from Post p,PostTag pt,Tag t where ");
        for(String query:keywords){
            sb.append("p.author like '%"+query+"%' or ");
        }
        sb.append("p.id=pt.postId and pt.tagId=t.id and ");
        for(String query:keywords){
            sb.append("t.name like '%"+query+"%' or ");
        }
        sb.delete(sb.length()-3,sb.length());
        System.out.println("query for author "+sb.toString());
        return sb.toString();
    }
}

