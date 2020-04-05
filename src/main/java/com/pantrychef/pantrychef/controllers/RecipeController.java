package com.pantrychef.pantrychef.controllers;
import com.pantrychef.pantrychef.models.Ingredient;
import com.pantrychef.pantrychef.models.Recipe;
import com.pantrychef.pantrychef.models.User;
import com.pantrychef.pantrychef.repositories.IngredientsRepo;
import com.pantrychef.pantrychef.repositories.RecipeRepo;
import com.pantrychef.pantrychef.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {

    private RecipeRepo recipeDao;
    private UserRepo userDao;
    private IngredientsRepo ingredientsDao;
    @Value("${filestack.api.key}")
    private String fsapi;

    public RecipeController(RecipeRepo recipeDao, UserRepo userDao, IngredientsRepo ingredientsDao) {
        this.recipeDao = recipeDao;
        this.userDao = userDao;
        this.ingredientsDao = ingredientsDao;
    }

    @GetMapping("/recipes")
    public String getPosts(Model model){
        model.addAttribute("recipes", recipeDao.findAll());
        return "recipes/recipes";
    }

    @GetMapping("/recipes/{id}")
    public String getPost(@PathVariable long id, Model model){
        model.addAttribute("recipe", recipeDao.findById(id));
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userId", u.getId());
        return "recipes/showRecipe";
    }

    //Edit a Recipe
//    @GetMapping("/recipe/{id}/edit")
//    public String editRecipeForm(Model model, @PathVariable long id){
//        model.addAttribute("recipe", recipeDao.getOne(id));
//        return "recipes/editRecipe";
//    }

    @GetMapping("/recipe/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        Recipe recipeToEdit = recipeDao.getOne(id);
        model.addAttribute("recipe", recipeToEdit);
        model.addAttribute("fsapi", fsapi);
        return "recipes/editRecipe";
    }

    @PostMapping("/recipe/{id}/edit")
    public String updateRecipe(@ModelAttribute Recipe recipe, @PathVariable long id){
        Recipe recipeToEdit = recipeDao.getOne(id);
        recipeToEdit.setTitle(recipeToEdit.getTitle());
        recipeToEdit.setDirections(recipeToEdit.getDirections());
//        recipeToEdit.setIngredient(recipeToEdit.getIngredient());
        recipeDao.save(recipe);
        return "redirect:/recipes";
    }

//    @PostMapping("/recipe/{id}/edit")
//    public String updatePost(@PathVariable long id, @RequestParam String title, @RequestParam String ingredient, @RequestParam String directions) {
//        Recipe r = recipeDao.getOne(id);
//        r.setTitle(title);
//        r.setIngredient(ingredient);
//        r.setDirections(directions);
//        recipeDao.save(r);
//        return "redirect:/recipes";
//    }


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
    public String createRecipe(@RequestParam(name= "ingredient-param") List<String> ingredientsStringList, @RequestParam String title, @RequestParam String directions, @RequestParam(name = "recipeImageUrl") String recipeImageUrl){
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
//        recipe.setIngredient(ingredients);
        recipe.setDirections(directions);
        recipe.setRecipeImageUrl(recipeImageUrl);
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setUser(u);
        recipeDao.save(recipe);

        List<Ingredient> recipeIngredientList = new ArrayList<>();

        for(String ingredient : ingredientsStringList){
            if(ingredient != ""){
                if(ingredientsDao.findIngredientByname(ingredient) == null) {
                    Ingredient addIngredient = new Ingredient();
                    addIngredient.setName(ingredient);
                    List<Recipe> recipeList = new ArrayList<>();
                    recipeList.add(recipe);
                    addIngredient.setRecipeList(recipeList);
                    recipeIngredientList.add(ingredientsDao.save(addIngredient));
                }else{
                    Ingredient updateIngredient = ingredientsDao.findIngredientByname(ingredient);
                    List<Recipe> recipeList = updateIngredient.getRecipeList();
                    recipeList.add(recipe);
                    updateIngredient.setRecipeList(recipeList);
                    recipeIngredientList.add(ingredientsDao.save(updateIngredient));

                }
            }
        }
        recipe.setIngredientList(recipeIngredientList);
        recipeDao.save(recipe);

        return "redirect:/recipes";
    }

    //Delete a recipe post
    @PostMapping("/recipe/{id}/delete")
    public String delete(@PathVariable long id){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == recipeDao.getOne(id).getUser().getId())
            // delete post
            recipeDao.deleteById(id);

        return "redirect:/recipes";
    }
}
