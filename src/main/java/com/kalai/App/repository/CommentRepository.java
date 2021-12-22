package com.kalai.App.repository;

import com.kalai.App.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Repository
public interface CommentRepository extends CrudRepository<Comment,Long> {

    public List<Comment> findByCommentedPostId(long id);

    @Modifying
    @Transactional
    @Query(value="select commented_post_id from comment where comment_id=?1",nativeQuery = true)
    public int getPostIdByCommentId(Long id);

}
