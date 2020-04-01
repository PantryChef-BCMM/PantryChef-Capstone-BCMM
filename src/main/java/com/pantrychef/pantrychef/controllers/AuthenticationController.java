package com.pantrychef.pantrychef.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    @GetMapping("/logout")
    @ResponseBody
    public String showLogoutForm() {
        return "You have been logged out!";
    }
}



