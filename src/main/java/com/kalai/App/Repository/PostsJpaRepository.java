package com.kalai.App.Repository;

import com.kalai.App.Entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsJpaRepository extends JpaRepository<Posts,Long> {

}
