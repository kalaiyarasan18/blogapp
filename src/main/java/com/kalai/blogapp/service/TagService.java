package com.kalai.blogapp.service;

import com.kalai.blogapp.entity.PostTag;
import com.kalai.blogapp.entity.Tag;
import com.kalai.blogapp.repository.PostTagRepository;
import com.kalai.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostTagRepository postTagRepository;

    public String tagByPostId(long postId) {
        List<String> tags=tagRepository.findByPostId(postId);
        StringBuilder sb=new StringBuilder();
        for(String eachTag:tags){
            sb.append(eachTag+" ");
        }
        return sb.toString();
    }

    public Tag getTagById(long id) {
        return tagRepository.findById(id);
    }

}
