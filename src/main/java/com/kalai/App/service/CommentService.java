package com.kalai.App.service;

import com.kalai.App.entity.Comment;
import com.kalai.App.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {

    @Autowired
    public CommentRepository commentRepository;
    public void saveComment(String comment, long postId, Date createdAt, Date publishedAt){
        Comment tempComment=new Comment(comment,postId,createdAt,publishedAt);
        commentRepository.save(tempComment);
        System.out.print(tempComment);
    }

}
