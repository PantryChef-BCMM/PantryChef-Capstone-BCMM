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
        return  "...This is the PantryChef Landing Page!...";
    }

    @GetMapping("/home")
    @ResponseBody
    public String welcome() {
        return "home";
    }
}
