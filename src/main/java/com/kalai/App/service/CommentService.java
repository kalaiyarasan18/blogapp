package com.kalai.App.service;

import com.kalai.App.entity.Comment;
import com.kalai.App.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    public CommentRepository commentRepository;

    public void saveComment(Comment comment){
        commentRepository.save(comment);
        System.out.print(comment);
    }
    public List<Comment> commentById(long id){
        List<Comment> commentList=commentRepository.findByCommentedPostId(id);
        System.out.print("List of comment by id:"+commentList);
        return commentList;
    }
    public void handleSave(Comment comment){
        Date currentTime=new Date();
        comment.setCommentCreatedAt(currentTime);
        comment.setCommentUpdatedAt(currentTime);
        commentRepository.save(comment);
    }
}
