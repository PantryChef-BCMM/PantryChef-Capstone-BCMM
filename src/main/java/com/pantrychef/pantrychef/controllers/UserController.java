package com.pantrychef.pantrychef.controllers;

import com.pantrychef.pantrychef.models.User;
import com.pantrychef.pantrychef.repositories.UserRepo;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserRepo users;

//============ THIS SECTION SHOULD BE USED ONCE HASHING PASSWORDS WORKS!!! ==========//
//    private PasswordEncoder passwordEncoder;

//    public UserController(UserRepo users, PasswordEncoder passwordEncoder) {
//        this.users = users;
//        this.passwordEncoder = passwordEncoder;
//    }
//===================================================================================//

//    TEMPORARY CONSTRUCTOR - GET RID OF IT ONCE HASHING WORKS!!!
    public UserController(UserRepo users) {
        this.users = users;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
//        String hash = passwordEncoder.encode(user.getPassword());
//        user.setPassword(hash);
        users.save(user);
        return "redirect:/login";
    }
}
