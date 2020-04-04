package com.pantrychef.pantrychef.controllers;

import com.pantrychef.pantrychef.models.Recipe;
import com.pantrychef.pantrychef.models.User;
import com.pantrychef.pantrychef.repositories.RecipeRepo;
import com.pantrychef.pantrychef.repositories.UserRepo;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    private UserRepo users;
    private RecipeRepo recipeDao;
    private PasswordEncoder passwordEncoder;
    private RecipeRepo recipeDao;
    @Value("${filestack.api.key}")
    private String fsapi;

    public UserController(UserRepo users, PasswordEncoder passwordEncoder, RecipeRepo recipeDao) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.recipeDao = recipeDao;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("fsapi", fsapi);
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        users.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
<<<<<<< HEAD
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", u);
        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("fsapi", fsapi);
//        model.addAttribute("user", CurrentUser);
=======
        User CurrentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("fsapi", fsapi);
        model.addAttribute("user", CurrentUser);
        model.addAttribute("recipes", recipeDao.findAll());
>>>>>>> 9b08edc2aff19bdc1d0dcb0129cd44b9889c26a4
        return "recipes/profile";
    }
}
