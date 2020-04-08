package com.pantrychef.pantrychef.controllers;

import com.pantrychef.pantrychef.models.Recipe;
import com.pantrychef.pantrychef.models.User;
import com.pantrychef.pantrychef.repositories.RecipeRepo;
import com.pantrychef.pantrychef.repositories.UserRepo;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController {
    private UserRepo usersDao;
    private RecipeRepo recipeDao;
    private PasswordEncoder passwordEncoder;

    @Value("${filestack.api.key}")
    private String fsapi;

    public UserController(UserRepo usersDao, PasswordEncoder passwordEncoder, RecipeRepo recipeDao) {
        this.usersDao = usersDao;
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
        user.setProfileImageUrl("https://picsum.photos/200");
        usersDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", u);
        model.addAttribute("recipes", recipeDao.findAll());
        model.addAttribute("fsapi", fsapi);
        return "recipes/profile";
    }

    @GetMapping("/profile/{username}")
    public String viewUserProfile(Model model, @PathVariable long id, @PathVariable username) {
            User u = usersDao.getOne(id);
            model.addAttribute("user", u);
            model.addAttribute("recipes", recipeDao.findAll());

        return "recipes/profileFromRecipe";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable long id) {
        User u = usersDao.getOne(id);
        model.addAttribute("user", u);
        model.addAttribute("fsapi", fsapi);
        return "users/editProfile";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable long id) {
        User updatedUser = usersDao.getOne(id);
        updatedUser.setEmail(user.getEmail());
        updatedUser.setFirst_name(user.getFirst_name());
        updatedUser.setLast_name(user.getLast_name());
        updatedUser.setUsername(user.getUsername());
//        updatedUser.setProfileImageUrl(u.getProfileImageUrl());
        updatedUser.setProfileImageUrl("https://picsum.photos/200");
        String hash = passwordEncoder.encode(user.getPassword());
        updatedUser.setPassword(hash);
        usersDao.save(updatedUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUser, updatedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/profile";
    }


}
