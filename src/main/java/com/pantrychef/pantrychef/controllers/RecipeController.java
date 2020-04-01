package com.pantrychef.pantrychef.controllers;
import com.pantrychef.pantrychef.models.Recipe;
import com.pantrychef.pantrychef.models.RecipeImages;
import com.pantrychef.pantrychef.models.User;
import com.pantrychef.pantrychef.repositories.RecipeRepo;
import com.pantrychef.pantrychef.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RecipeController {

    private RecipeRepo recipeDao;
    private UserRepo userDao;

    @Value("${filestack.api.key}")
    private String fsapi;

    public RecipeController(RecipeRepo recipeDao, UserRepo userDao) {
        this.recipeDao = recipeDao;
        this.userDao = userDao;
    }

    @GetMapping("/recipes")
    public String getPosts(Model model){
        model.addAttribute("recipes", recipeDao.findAll());
        return "recipes/search";
    }


    @GetMapping("/recipes/{id}")
    public String getPost(@PathVariable long id, Model model){
        model.addAttribute("recipe", recipeDao.findById(id));
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userId", u.getId());
        return "recipes/showRecipe";
    }

    //Edit a Recipe
    @GetMapping("/recipe/{id}/edit")
    public String editRecipeForm(Model model, @PathVariable long id){
        model.addAttribute("recipe", recipeDao.getOne(id));
        return "recipes/editRecipe";
    }

    @PostMapping("/recipe/edit")
    public String updateRecipe(@ModelAttribute Recipe recipe){
//        Recipe recipeToEdit = recipeDao.getOne(id);
//        recipeToEdit.setTitle(recipeToEdit.getTitle());
//        recipeToEdit.setDirections(recipeToEdit.getDirections());
//        recipeToEdit.setIngredient(recipeToEdit.getIngredient());
        recipeDao.save(recipe);
        return "redirect:/recipes";
    }


    //Create a recipe
    @GetMapping("/recipe/create")
    public String createForm(Model model){
//        User CurrentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        model.addAttribute("user", CurrentUser);
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("fsapi", fsapi);
        return "recipes/postRecipe";
    }

    @PostMapping("/recipe/create")
    public String createRecipe(@RequestParam String title, @RequestParam String ingredients, @RequestParam String directions, @RequestParam List<RecipeImages> path){
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setIngredient(ingredients);
        recipe.setDirections(directions);
        recipe.setImages(path);
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setUser(u);
        recipeDao.save(recipe);
        return "redirect:/recipes/recipes";
    }

    //Delete a post
    @PostMapping("/recipes/delete/{id}")
    public String deleteRecipe(@PathVariable long id){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == recipeDao.getOne(id).getUser().getId())
            recipeDao.deleteById(id);
        return "redirect:/recipes/recipes";
    }
}
