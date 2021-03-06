package com.kalai.blogapp.repository;

import com.kalai.blogapp.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findByPostId(long postId);

    void deleteByPostId(long postId);
}
