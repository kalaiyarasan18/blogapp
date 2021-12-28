package com.kalai.blogapp.repository;

import com.kalai.blogapp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByTagName(String tag);

    public Tag findByTagName(String tag);

    public Tag findById(long tagId);

    @Query(value = "select tag_name from tag t inner join post_tags pt on t.id=pt.tag_id where pt.post_id=?1",nativeQuery = true)
    public List<String> findByPostId(long postId);

}
