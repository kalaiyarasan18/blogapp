package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
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

}
