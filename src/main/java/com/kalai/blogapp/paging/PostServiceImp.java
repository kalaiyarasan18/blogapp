package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.entity.PostTag;
import com.kalai.blogapp.entity.Tag;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.repository.PostTagRepository;
import com.kalai.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;
import java.util.*;

import static com.kalai.blogapp.controller.PostController.globalPost;

@Service
public class PostServiceImp implements PostServicePagable {
    @Resource
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostTagRepository postTagRepository;
    List<Long> postIdsForTagName = new ArrayList<>();
    @Autowired
    private EntityManager entityManager;

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
            tags.add(tag.getName());
        }
        return tags;
    }

    public List<String> getAllAuthors() {
        List<Post> allPosts = postRepository.findAll();
        List<String> authors = new ArrayList<>();
        for (Post post : allPosts) {
            authors.add(post.getAuthor());
        }
        Set<String> authorSet = new TreeSet<>();
        authorSet.addAll(authors);
        authors.clear();
        authors.addAll(authorSet);
        return authors;
    }

    public List<Post> searchAllBySearch(String search) {
        Set<Post> postSet = new TreeSet<>();
        String[] searchQuery = search.split(",");
        for (String query : searchQuery) {
            postSet.addAll(postRepository.findAllBySearch(query));
        }
        System.out.println("search query: " + search);
        System.out.println("global list: " + globalPost.size());
        System.out.println("searchresult list: " + postSet.size());
        List<Long> globalPostId = new ArrayList<>();
        for (Post post : globalPost) {
            globalPostId.add(post.getId());
        }
        System.out.println("global post ids: " + globalPostId.size());
        Set<Post> set = new HashSet<>();
        for (Post eachPost : postSet) {
            System.out.println(eachPost.getId() + "==" + globalPostId.contains(eachPost.getId()));
            if (globalPostId.contains(eachPost.getId())) {
                set.add(eachPost);
            }
        }
        System.out.println("final filtered: " + set);
        System.out.println("before global size: " + globalPost.size());
        globalPost.clear();
        globalPost.addAll(set);
        System.out.println("after global size: " + globalPost.size());
        return globalPost;
    }

    @Override
    public Page<Post> findAllPages(int offset, int limit) {
        Pageable pageable= PageRequest.of(offset, limit);
        System.out.println(postRepository.findAll(pageable).getContent().size());
        return postRepository.findAll(pageable);
    }

    public PagedListHolder<Post> findPage(List<Post> posts){
        PagedListHolder<Post> pageList=new PagedListHolder<>(posts);
        return pageList;
    }

    @Transactional
    public List<Post> processQuery(String toString) {
        TypedQuery<Post> query=entityManager.createQuery(toString,Post.class);
        //System.out.println("processed Query; \n\n"+toString);
        return query.getResultList();
    }
}

