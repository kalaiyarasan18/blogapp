package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositoryPagable extends JpaRepository<Post, Long> {
    public Page<Post> findAll(Pageable pageable);

}
