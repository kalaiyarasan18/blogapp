package com.kalai.blogapp.service;

import com.kalai.blogapp.repository.PostTagRepository;
import com.kalai.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostTagRepository postTagRepository;

    public String tagByPostId(long postId) {
        List<String> tags = tagRepository.findByPostId(postId);
        StringBuilder stringBuilder = new StringBuilder();
        for (String eachTag : tags) {
            stringBuilder.append(" # " + eachTag);
        }
        return stringBuilder.toString();
    }

}
