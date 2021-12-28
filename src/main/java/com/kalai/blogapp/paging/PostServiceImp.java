package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.entity.PostTag;
import com.kalai.blogapp.entity.Tag;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.repository.PostTagRepository;
import com.kalai.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

import static com.kalai.blogapp.controller.PostController.globalPost;

@Service
public class PostServiceImp implements PostServicePagable {
    @Resource
    PostRepositoryPagable postRepositoryPagable;
    @Autowired
    PostRepository postRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostTagRepository postTagRepository;
    List<Long> postIdsForTagName = new ArrayList<>();
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Post> findAll(int offset, int limit) {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post as p order by p.postPublishedAt", Post.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public void deleteTagByPostId(long postId) {
        List<PostTag> list = postTagRepository.findByPostId(postId);
        for (PostTag postTag : list) {
            long tagId = postTag.getTagId();
            System.out.println("tag id to delete:" + tagId);
            tagRepository.deleteById(tagId);
        }
    }

    public List<String> getAllTags() {
        List<String> tags = new ArrayList<>();
        List<Tag> allTags = tagRepository.findAll();
        for (Tag tag : allTags) {
            tags.add(tag.getTagName());
        }
        return tags;
    }

    public List<String> getAllAuthors() {
        List<Post> allPosts = postRepository.findAll();
        List<String> authors = new ArrayList<>();
        for (Post post : allPosts) {
            authors.add(post.getPostAuthor());
        }
        Set<String> authorSet = new TreeSet<>();
        authorSet.addAll(authors);
        authors.clear();
        authors.addAll(authorSet);
        return authors;
    }

    public List<Post> searchAllBySearch(String search){
        Set<Post> postSet=new TreeSet<>();
        List<Post> postWithoutDuplicate=new ArrayList<>();
        String[] searchQuery=search.split(",");
        for(String query:searchQuery){
            postSet.addAll(postRepository.findAllBySearch(query));
        }
        postWithoutDuplicate.addAll(postSet);
        List<Post> filteredPost=new ArrayList<>();
        for(Post eachPost:postWithoutDuplicate){
            if(globalPost.contains(eachPost)){
                filteredPost.add(eachPost);
            }
        }
        return postWithoutDuplicate;
    }


    public boolean checkIfPresent(Post post,List<Post> list){
        boolean res=false;
        for(Post p:list){
            if(p.equals(post)){
                res=true;
            }
        }
        return res;
    }
}

