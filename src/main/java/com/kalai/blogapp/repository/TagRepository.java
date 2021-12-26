package com.kalai.blogapp.repository;

import com.kalai.blogapp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByTagName(String tag);

    public Tag findByTagName(String tag);

    public Tag findById(long tagId);

}
