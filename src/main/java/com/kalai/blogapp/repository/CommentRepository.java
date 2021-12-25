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

    public Comment findCommentedPostIdByCommentId(long postId);

    @Modifying
    @Transactional
    @Query(value = "select commented_post_id from comment where comment_id=?1", nativeQuery = true)
    public Comment getPostIdByCommentId(int id);

    List<Comment> findByCommentedPostId(long id);

    /*@Modifying
    @Transactional
    @Query("update Comment c set c.commenterName = ?1, c.commenterEmail = ?2, c.postContent=?3, " +
            "c.commentUpdatedAt=?4 where c.commentId = ?5")
    void updateComment(String commenterName, String commenterEmail, String commentContent,
                       Date currentTime, Date currentTime1, long commentId);*/

    @Modifying
    @Transactional
    @Query("update Comment c set c.commenterName = ?1, c.commenterEmail = ?2," +
            " c.commentContent=?3,c.commentUpdatedAt=?4 where c.commentId = ?5")
    void updateComment(String name, String email, String content, Date updatedAt, Long postId);

    Comment findById(long id);
}
