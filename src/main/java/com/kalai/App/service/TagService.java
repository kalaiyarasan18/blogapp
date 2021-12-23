package com.kalai.App.service;

import com.kalai.App.entity.Tag;
import com.kalai.App.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public void create(String name) {
        Tag tag=new Tag();
        Date currentTime=new Date();
        tag.setTagName(name);
        tag.setTagCreatedAt(currentTime);
        tag.setTagUpdatedAt(currentTime);
        tagRepository.save(tag);
    }

    public void mapTagToPost(String tag,long postId){
        String[] extractedTags=tag.split(",");
        for(int i=0;i<extractedTags.length;i++){
            String eachTag=extractedTags[i];
            if(tagRepository.existsByTagName(eachTag)){
            Tag tempTag=new Tag();
            tempTag.setTagName(extractedTags[i]);
            tempTag.setTagCreatedAt(new Date());
            tempTag.setTagUpdatedAt(new Date());
            tagRepository.save(tempTag);
            }
            int tagId=tagRepository.findTagIdByTagName(eachTag);

        }
    }
    public void mapTag(String tag,long postId){

    }
}
