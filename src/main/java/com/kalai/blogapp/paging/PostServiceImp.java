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
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
/*

    public List<Post> searchAllBySearch(String search) {
        Set<Post> postSet = new TreeSet<>();
        String[] searchQuery = search.split(",");
        for (String query : searchQuery) {
            postSet.addAll(postRepository.findAllBySearch(query));
        }
        List<Long> globalPostId = new ArrayList<>();
        for (Post post : globalPost) {
            globalPostId.add(post.getId());
        }
        Set<Post> set = new HashSet<>();
        for (Post eachPost : postSet) {
            System.out.println(eachPost.getId() + "==" + globalPostId.contains(eachPost.getId()));
            if (globalPostId.contains(eachPost.getId())) {
                set.add(eachPost);
            }
        }
        globalPost.clear();
        globalPost.addAll(set);
        return globalPost;
    }
*/

    @Override
    public Page<Post> findAllPages(int offset, int limit) {
        Pageable pageable= PageRequest.of(offset, limit);
        return postRepository.findAll(pageable);
    }

    public PagedListHolder<Post> findPage(List<Post> posts){
        PagedListHolder<Post> pageList=new PagedListHolder<>(posts);
        return pageList;
    }

    @Transactional
    public List<Post> processQuery(String stringQuery) {
        TypedQuery<Post> query=entityManager.createQuery(stringQuery,Post.class);
        System.out.println("Typed Query: "+stringQuery);
        return query.getResultList();
    }
}

