package com.pantrychef.pantrychef.controllers;

import com.pantrychef.pantrychef.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Value("${filestack.api.key}")
    private String fsapi;

    @GetMapping("/")
    public String landing() {
        return "recipes/landingPage";
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
    public String profile(Model model) {
        User CurrentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("fsapi", fsapi);
        model.addAttribute("user", CurrentUser);
        return "recipes/profile";
    }

    @GetMapping("/create")
    public String create() {
        return "recipes/postRecipe";
    }
}

//    @GetMapping("/posts")
//    public String getPosts(Model model){
//        model.addAttribute("posts", postDao.findAll());
//        return "posts/index";
//    }