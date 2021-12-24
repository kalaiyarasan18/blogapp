package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostServicePagable {
    public List<Post> findAll(int offset, int limit);
}
