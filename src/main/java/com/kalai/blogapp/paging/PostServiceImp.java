package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NamedNativeQuery;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class PostServiceImp implements PostServicePagable {
    @Resource
    PostRepositoryPagable postRepositoryPagable;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Post> findAll(int offset, int limit) {
        TypedQuery<Post> query = entityManager.createQuery(
                "SELECT p FROM Post as p", Post.class);
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

}
