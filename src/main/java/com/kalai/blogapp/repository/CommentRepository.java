package com.kalai.blogapp.repository;

import com.kalai.blogapp.entity.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    Comment findPostIdById(long postId);

    @Modifying
    @Transactional
    @Query(value = "select post_id from comment where id=?1", nativeQuery = true)
    Comment getPostIdById(int id);

    List<Comment> findByPostId(long id);

    @Modifying
    @Transactional
    @Query("update Comment c set c.name = ?1, c.email = ?2," +
            " c.content=?3,c.updatedAt=?4 where c.id = ?5")
    void updateComment(String name, String email, String content, Date updatedAt, Long postId);

    Comment findById(long id);
}
