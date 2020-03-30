package com.pantrychef.pantrychef.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

//
//    public UserController(UserRepo users, PasswordEncoder passwordEncoder) {
//        this.users = users;
//        this.passwordEncoder = passwordEncoder;
//    }

    @GetMapping("/sign-up")
    public String showSignupForm(){
        return "users/sign-up";
    }

//    @PostMapping("/sign-up")
//    public String saveUser(){
//        return "redirect:/login";
//    }
}
