package com.kalai.blogapp.service;

import com.kalai.blogapp.entity.Comment;
import com.kalai.blogapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    public CommentRepository commentRepository;

    public List<Comment> commentById(long id) {
        List<Comment> commentList = commentRepository.findByCommentedPostId(id);
        return commentList;
    }

    public void handleSave(Comment comment) {
        Date currentTime = new Date();
        comment.setCommentCreatedAt(currentTime);
        comment.setCommentUpdatedAt(currentTime);
        commentRepository.save(comment);
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    public Comment getPostById(long id) {
        Comment comment = commentRepository.findCommentedPostIdByCommentId(id);
        return comment;
    }

    public long getIdForPost(long id) {
        Comment comment = commentRepository.findCommentedPostIdByCommentId(id);
        return comment.getCommentedPostId();
    }

    public void update(Comment comment) {
        System.out.println("inside service :" + comment);
        Comment comment1 = commentRepository.findById(comment.getCommentId());
        comment1.setCommentContent(comment.getCommentContent());
        comment1.setCommenterEmail(comment.getCommenterEmail());
        comment1.setCommenterName(comment.getCommenterName());
        comment1.setCommentUpdatedAt(comment.getCommentUpdatedAt());
        commentRepository.save(comment1);
    }
}
