package com.pantrychef.pantrychef.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String landing() {
        return "This is the landing page!";
    }

    @GetMapping("/index")
    public String index() {
        return  "recipes/index";
    }

    @GetMapping("/home")
    @ResponseBody
    public String welcome() {
        return "home";
    }

    @GetMapping("/profile")
    @ResponseBody
    public String profile() {
        return "profile";
    }

}

//    @GetMapping("/posts")
//    public String getPosts(Model model){
//        model.addAttribute("posts", postDao.findAll());
//        return "posts/index";
//    }