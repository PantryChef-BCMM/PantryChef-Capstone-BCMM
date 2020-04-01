package com.pantrychef.pantrychef.controllers;

import com.pantrychef.pantrychef.models.Ingredient;
import com.pantrychef.pantrychef.models.Recipe;
import com.pantrychef.pantrychef.repositories.RecipeRepo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class RecipeController {

    private RecipeRepo recipeDao;
    private UserRepo userDao;

    public RecipeController(RecipeRepo recipeDao, UserRepo userDao) {
        this.recipeDao = recipeDao;
        this.userDao = userDao;
    }

    @GetMapping("/recipes")
    public String getPosts(Model model){
        model.addAttribute("recipes", recipeDao.findAll());
        return "recipes/search";
    }

    @GetMapping("recipes/{id}")
    public String getPost(@PathVariable long id, Model model){
        model.addAttribute("recipe", recipeDao.findById(id));
        return "recipes/showRecipe";
    }

    @GetMapping("/recipe/{id}/edit")
    public String editRecipeForm(Model model, @PathVariable long id){
        Recipe getRecipeToEdit = recipeDao.getOne(id);
        model.addAttribute("recipe", getRecipeToEdit);
        return "redirect:/recipes/edit";
    }

    @PostMapping("recipe/{id}/edit")
    public String updateRecipe(@ModelAttribute Recipe recipe, @PathVariable long id){
        Recipe recipeToEdit = recipeDao.getOne(id);
        recipeToEdit.setTitle(recipeToEdit.getTitle());
        recipeToEdit.setDirections(recipeToEdit.getDirections());
        recipeToEdit.setIngredient(recipeToEdit.getIngredient());
        recipeDao.save(recipeToEdit);
        return "redirect:/recipes";
    }

    @GetMapping("recipe/create")
    public String createForm(Model model){
        model.addAttribute("recipe", new Recipe());
        return "recipe/create";
    }

    @PostMapping("recipe/create")
    public String createRecipe(String title, String ingredients, String directions){
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setIngredient(ingredients);
        recipe.setDirections(directions);
        recipeDao.save(recipe);
        return "redirect:/recipes";
    }

    @PostMapping("recipes/delete/{id}")
    public String deleteRecipe(@PathVariable long id){
//        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (loggedInUser.getId() == postDao.getOne(id).getUser().getId()){
//            // delete post
//            System.out.println(loggedInUser.getId());
//            System.out.println(postDao.getOne(id).getUser().getId());
//            System.out.println(postDao.getOne(id).getId());
//            postDao.deleteById(id);
//        }else{
//            return "redirect:/posts";
//        }
            recipeDao.deleteById(id);

        return "redirect:/recipes";
    }
}
