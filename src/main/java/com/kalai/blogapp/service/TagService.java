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

    public void create(String name) {
        Tag tag = new Tag();
        Date currentTime = new Date();
        tag.setTagName(name);
        tag.setTagCreatedAt(currentTime);
        tag.setTagUpdatedAt(currentTime);
        tagRepository.save(tag);
    }

    public void mapTagToPost(String tag, long postId) {
        String[] extractedTags = tag.split(",");
        System.out.println("Tag length is:" + extractedTags.length);
        for (int i = 0; i < extractedTags.length; i++) {
            String eachTag = extractedTags[i];
            if (tagRepository.existsByTagName(eachTag)) {
                Tag tagWithId = tagRepository.findByTagName(eachTag);
                PostTag postTag = new PostTag();
                postTag.setPostId(postId);
                postTag.setTagId(tagWithId.getTagId());
                postTag.setCreatedAt(new Date());
                postTag.setUpdatedAt(new Date());
                postTagRepository.save(postTag);
            } else {
                Tag tempTag = new Tag();
                tempTag.setTagName(eachTag);
                tempTag.setTagCreatedAt(new Date());
                tempTag.setTagUpdatedAt(new Date());
                Tag tagWithId = tagRepository.save(tempTag);
                PostTag postTag = new PostTag();
                postTag.setPostId(postId);
                postTag.setTagId(tagWithId.getTagId());
                postTag.setCreatedAt(new Date());
                postTag.setUpdatedAt(new Date());
                postTagRepository.save(postTag);
            }
        }
    }

    public String tagByPostId(long postId) {
        List<PostTag> postTagList = postTagRepository.findByPostId(postId);
        StringBuilder stringBuilder = new StringBuilder();
        for (PostTag postTag : postTagList) {
            long tagId = postTag.getTagId();
            Tag tagsById = tagRepository.findById(tagId);
            stringBuilder.append(tagsById.getTagName() + " ");
        }
        return stringBuilder.toString();
    }
}
