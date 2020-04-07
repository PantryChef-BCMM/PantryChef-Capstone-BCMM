package com.pantrychef.pantrychef.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String landing() {
        return "recipes/landingPage";
    }

    @GetMapping("/home")
    @ResponseBody
    public String welcome() {
        return "home";
    }

    @GetMapping("/aboutUs")
    public String about() {
        return "recipes/aboutUs";
    }

}

