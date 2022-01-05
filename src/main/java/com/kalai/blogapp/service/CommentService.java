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
        return commentRepository.findByPostId(id);
    }

    public void handleSave(Comment comment) {
        Date currentTime = new Date();
        comment.setCreatedAt(currentTime);
        comment.setUpdatedAt(currentTime);
        commentRepository.save(comment);
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    public Comment getPostById(long id) {
        Comment comment = commentRepository.findPostIdById(id);
        return comment;
    }

    public long getIdForPost(long id) {
        Comment comment = commentRepository.findPostIdById(id);
        return comment.getPostId();
    }

    public void update(Comment comment) {
        Comment commentToUpdate = commentRepository.findById(comment.getId());
        commentToUpdate.setContent(comment.getContent());
        commentToUpdate.setEmail(comment.getEmail());
        commentToUpdate.setName(comment.getName());
        commentToUpdate.setUpdatedAt(comment.getUpdatedAt());
        commentRepository.save(commentToUpdate);
    }
}
