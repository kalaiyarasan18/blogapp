package com.kalai.blogapp.controller;

import com.kalai.blogapp.entity.Users;
import com.kalai.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping(value="showregister")
    public String showRegisterForm(){
        return "register";
    }

    @PostMapping(value="register")
    public String register(@ModelAttribute("users")Users user){
        System.out.println("user = " + user);
        String plainpassword=user.getPassword();
        user.setPassword(encoder().encode(plainpassword));
        user.setAuthorities("author");
        userRepository.save(user);
        return "login";
    }



}
