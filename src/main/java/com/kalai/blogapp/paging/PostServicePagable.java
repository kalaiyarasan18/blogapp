package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;
import org.springframework.data.domain.Page;

public interface PostServicePagable {
    Page<Post> findAllPages(int offset, int limit);
}
