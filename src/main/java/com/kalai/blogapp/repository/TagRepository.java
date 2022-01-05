package com.kalai.blogapp.repository;

import com.kalai.blogapp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String tag);

    Tag findById(long tagId);

    @Query(value = "select name from tag t inner join post_tags pt on t.id=pt.tag_id where pt.post_id=?1", nativeQuery = true)
    List<String> findByPostId(long postId);

}
