package com.kalai.blogapp.paging;

import com.kalai.blogapp.entity.Post;

import java.util.List;

public interface PostServicePagable {
    public List<Post> findAll(int offset, int limit);
}
