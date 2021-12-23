package com.kalai.App.repository;

import com.kalai.App.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    boolean existsByTagName(String tag);
    int findTagIdByTagName(String tag);

}
