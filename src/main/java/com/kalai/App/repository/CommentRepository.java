package com.kalai.App.repository;

import com.kalai.App.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends CrudRepository<Comment,Long> {

    List<Comment> findByCommentedPostId(long id);

    Comment findById(long id);

    Long findCommentedPostIdByCommentId(long id);
}
