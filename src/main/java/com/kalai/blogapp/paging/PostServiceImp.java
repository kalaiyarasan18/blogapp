package com.kalai.blogapp.paging;

import com.kalai.blogapp.controller.PostController;
import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.entity.PostTag;
import com.kalai.blogapp.entity.Tag;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.repository.PostTagRepository;
import com.kalai.blogapp.repository.TagRepository;
import com.kalai.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.awt.print.Pageable;
import java.util.*;

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
    @Autowired
    private EntityManager entityManager;

    List<Long> postIdsForTagName=new ArrayList<>();

    @Transactional
    public List<Post> findAll(int offset, int limit) {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post as p order by p.postPublishedAt", Post.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional
    public List<Long> searchByTag(String keyword) {
        TypedQuery<Long> query = entityManager.createQuery(
                "select post_id from " +
                        "(select * from tag,post_tag where post_tag.tag_id=tag.tag_id) R " +
                        "where R.tag_name like '%Ios%'", Long.class);
        return query.getResultList();
    }
    public List<Long> searchByTag(String keyword,List<Post> list) {
        TypedQuery<Long> query = entityManager.createQuery(
                "select post_id from " +
                        "(select * from tag,post_tag where post_tag.tag_id=tag.tag_id) R " +
                        "where R.tag_name like '%Ios%'", Long.class);
        return query.getResultList();
    }


    public void deleteTagByPostId(long postId) {
        List<PostTag> list = postTagRepository.findByPostId(postId);
        for (PostTag postTag : list) {
            long tagId = postTag.getTagId();
            System.out.println("tag id to delete:"+tagId);
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
        Set<String> authorSet=new TreeSet<>();
        authorSet.addAll(authors);
        authors.clear();
        authors.addAll(authorSet);
        return authors;
    }

    public List<Post> getAllPostByTag(String tag) {
        List<Long> postIdForTag = postRepository.searchWithTag(tag);
        List<Post> posts = new ArrayList<>();
        if (postIdForTag.size() > 0) {
            for (long id : postIdForTag) {
                Post post = postRepository.findById(id);
                posts.add(post);
            }
        }
        postIdsForTagName = postIdForTag;
        return posts;
    }

    /*public List<Post> handleFilter(String author, String tag) {
        List<Post> filteredResult = new ArrayList<>();
        if (author != null) {
            String[] selectedAuthors = author.split(",");
            for (String eachAuthor : selectedAuthors) {
                Post post= (Post) postRepository.findByPostAuthor(eachAuthor);
                System.out.println("post in each post of filter:"+post);
                filteredResult.add(post);
            }
        }
        if (tag != null) {
            String[] selectedTag = author.split(",");
            for (String eachTag : selectedTag) {
                filteredResult.addAll(getAllPostByTag(eachTag));
            }
        }
        return filteredResult;
    }*/
    public List<Post> handleFilter(String author){
        List<Post> tempList=new ArrayList<>();
        System.out.println("inside the filterhandling():\n");
        String[] authors=author.split(",");
        for(String authorName:authors){
        for(Post post: globalPost) {
            if (post.getPostAuthor().equals(author)) {
                tempList.add(post);
            }
        }
        }
        return tempList;
    }

    public List<Post> postListForTagName(String tag){
        List<Post> tempListForPosts=new ArrayList<>();
        for(long id:postIdsForTagName){
            tempListForPosts.add(postRepository.findById(id));
        }
        return tempListForPosts;
    }

    public List<Post> handleFilterForTag(String tag) {
        Set<Long> postIdsForTags=new HashSet<>();
        List<Post> postsForGivenTag=new ArrayList<>();
        String[] tags=tag.split(",");
        for(String tagName:tags){
            postIdsForTags.addAll(postRepository.searchWithTag(tag));
        }
        for(long postId:postIdsForTags){
            postsForGivenTag.add(postRepository.findById(postId));
        }
        return postsForGivenTag;
    }
}