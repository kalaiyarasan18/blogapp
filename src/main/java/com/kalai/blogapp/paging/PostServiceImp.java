package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;
import com.kalai.blogapp.entity.PostTag;
import com.kalai.blogapp.entity.Tag;
import com.kalai.blogapp.repository.PostRepository;
import com.kalai.blogapp.repository.PostTagRepository;
import com.kalai.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImp implements PostServicePagable {
    public static List<Post> globalPost;
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

    @Transactional
    public List<Post> findAll(int offset, int limit) {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post as p order by p.postPublishedAt", Post.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public Pageable getPageableAsc(Integer offset, Integer limit, String sortBy) {
        Pageable pageable = (Pageable) PageRequest.of(offset, limit, Sort.by(sortBy).ascending());
        return pageable;
    }

    public Pageable getPageableDsc(Integer offset, Integer limit, String sortBy) {
        Pageable pageable = (Pageable) PageRequest.of(offset, limit, Sort.by(sortBy).descending());
        return pageable;
    }

    @Transactional
    public List<Long> searchByTag(String keyword) {
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
            tagRepository.deleteById(tagId);
        }
        postTagRepository.deleteByPostId(postId);
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
        return authors;
    }

    public List<Post> getAllPostByAuthor(String authors) {
        List<Post> postByAuthor = postRepository.findByPostAuthor(authors);
        return postByAuthor;
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
        return posts;
    }

    public List<Post> handleFilter(String author, String tag, String date) {
        List<Post> filteredResult = new ArrayList<>();
        if (author != null) {
            String[] selectedAuthors = author.split(",");
            for (String eachAuthor : selectedAuthors) {
                filteredResult.addAll(postRepository.findByPostAuthor(eachAuthor));
            }
        }
        if (tag != null) {
            String[] selectedTag = author.split(",");
            for (String eachTag : selectedTag) {
                filteredResult.addAll(getAllPostByTag(eachTag));
            }
        }
        if (date != null) {
            System.out.println("Selected date is:" + date + date.getClass());
            System.out.println("printing date using new date:" + new Date());
        }
        return filteredResult;
    }
}
