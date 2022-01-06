package com.kalai.blogapp.repository;

import com.kalai.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findById(long id);

    @Modifying
    @Query("update Post p set p.title = ?2, p.excerpt = ?3,p.content=?4,p.author=?5," +
            "p.updatedAt=?6 where p.id = ?1")
    void updatePost(long id, String title, String excerpt,
                    String content, String author, Date date);

    @Query(value = "SELECT * FROM post p INNER JOIN post_tags pt ON p.id = pt.post_id INNER JOIN tag t ON pt.tag_id = t.id " +
            "where p.title like %?1% or p.author like %?1% or p.content like %?1% or t.name like %?1%", nativeQuery = true)
    List<Post> findAllBySearch(String search);



}





