package com.kalai.blogapp.repository;

import com.kalai.blogapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {

    public Users findByUsername(String name);
}
