package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostServicePagable {
    Page<Post> findAllPages(int offset, int limit);
}
