package com.pantrychef.pantrychef.controllers;

import com.pantrychef.pantrychef.models.Ingredient;
import com.pantrychef.pantrychef.models.Recipe;
import com.pantrychef.pantrychef.models.User;
import com.pantrychef.pantrychef.repositories.IngredientsRepo;
import com.pantrychef.pantrychef.repositories.RecipeRepo;
import com.pantrychef.pantrychef.repositories.UserRepo;

import com.pantrychef.pantrychef.models.*;
import com.pantrychef.pantrychef.repositories.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {

    private RecipeRepo recipeDao;
    private UserRepo userDao;
    private IngredientsRepo ingredientsDao;
    private CategoriesRepo categoriesDao;
    private CommentsRepo commentsDao;
    @Value("${filestack.api.key}")
    private String fsapi;


    public RecipeController(RecipeRepo recipeDao, UserRepo userDao, IngredientsRepo ingredientsDao, CategoriesRepo categoriesDao, CommentsRepo commentsDao) {

        this.recipeDao = recipeDao;
        this.userDao = userDao;
        this.ingredientsDao = ingredientsDao;
        this.categoriesDao = categoriesDao;
        this.commentsDao = commentsDao;
    }

    @GetMapping("/recipes")
    public String getPosts(Model model, @RequestParam(required = false) String search) {
        model.addAttribute("search", search);
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", u);
            if (search == null) {
                List<Recipe> recipes = recipeDao.findAll();
                model.addAttribute("recipes", recipes);
            } else if (search.length() != 0) {
                List<Recipe> recipes = recipeDao.findAll();
                List<Recipe> searchedRecipes = new ArrayList<>();
                for (Recipe recipe : recipes) {
                    if (recipe.getTitle().toLowerCase().contains(search.toLowerCase())){
                        searchedRecipes.add(recipe);
                    }
                    model.addAttribute("recipes", searchedRecipes);
                }
            }
        }
        else if (search == null) {
            List<Recipe> recipes = recipeDao.findAll();
            model.addAttribute("recipes", recipes);
        } else if (search.length() != 0) {
            List<Recipe> recipes = recipeDao.findAll();
            List<Recipe> searchedRecipes = new ArrayList<>();
            for (Recipe recipe : recipes) {
                if (recipe.getTitle().toLowerCase().contains(search.toLowerCase())){
                    searchedRecipes.add(recipe);
                }
                model.addAttribute("recipes", searchedRecipes);
            }
        }
        System.out.println(search);
        return "recipes/recipes";
    }

    @GetMapping("/recipes/{id}")
    public String getPost(@PathVariable long id, Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", u);
        } else {
            model.addAttribute("recipe", recipeDao.getOne(id));
            return "recipes/showRecipe";
        }
        Recipe recipe = recipeDao.getOne(id);
        List comments = recipe.getComments();
        model.addAttribute("comments", comments);
        model.addAttribute("recipe", recipe);
        return "recipes/showRecipe";
    }

    @GetMapping("/recipe/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        Recipe recipeToEdit = recipeDao.getOne(id);
        model.addAttribute("recipe", recipeToEdit);
        model.addAttribute("fsapi", fsapi);
        return "recipes/editRecipe";
    }

    @PostMapping("/recipe/{id}/edit")
    public String updateRecipe(@ModelAttribute Recipe recipe, @PathVariable long id, @RequestParam(name = "ingredient-param") List<String> ingredientsStringList, @RequestParam List<Categories> categories) {
        Recipe recipeToEdit = recipeDao.getOne(id);
        recipeToEdit.setTitle(recipeToEdit.getTitle());
        recipeToEdit.setDirections(recipeToEdit.getDirections());
        recipe.setRecipeImageUrl("https://picsum.photos/200"); //@RequestParam(name = "recipeImageUrl") String recipeImageUrl,
        recipe.setCategories(categories);
        List<Ingredient> recipeIngredientList = new ArrayList<>();
        for (String ingredient : ingredientsStringList) {
            if (ingredient != "") {
                if (ingredientsDao.findIngredientByname(ingredient) == null) {
                    Ingredient addIngredient = new Ingredient();
                    addIngredient.setName(ingredient);
                    List<Recipe> recipeList = new ArrayList<>();
                    recipeList.add(recipe);
                    addIngredient.setRecipeList(recipeList);
                    recipeIngredientList.add(ingredientsDao.save(addIngredient));
                } else {
                    Ingredient updateIngredient = ingredientsDao.findIngredientByname(ingredient);
                    List<Recipe> recipeList = updateIngredient.getRecipeList();
                    recipeList.add(recipe);
                    updateIngredient.setRecipeList(recipeList);
                    recipeIngredientList.add(ingredientsDao.save(updateIngredient));
                }
            }
        }
        recipe.setIngredientList(recipeIngredientList);
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setUser(u);
        recipeDao.save(recipe);
        return "redirect:/recipes";
    }

    //Create a recipe
    @GetMapping("/recipe/create")
    public String createForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("fsapi", fsapi);
        return "recipes/createRecipe";
    }

    @PostMapping("/recipe/create")

    public String createRecipe(@RequestParam(name = "ingredient-param") List<String> ingredientsStringList, @RequestParam String title, @RequestParam String directions, @RequestParam List<Categories> categories) {
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDirections(directions);
        recipe.setRecipeImageUrl("https://picsum.photos/200"); //@RequestParam(name = "recipeImageUrl") String recipeImageUrl,
        recipe.setCategories(categories);
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setUser(u);
        recipeDao.save(recipe);

        List<Ingredient> recipeIngredientList = new ArrayList<>();

        for (String ingredient : ingredientsStringList) {
            if (ingredient != "") {
                if (ingredientsDao.findIngredientByname(ingredient) == null) {
                    Ingredient addIngredient = new Ingredient();
                    addIngredient.setName(ingredient);
                    List<Recipe> recipeList = new ArrayList<>();
                    recipeList.add(recipe);
                    addIngredient.setRecipeList(recipeList);
                    recipeIngredientList.add(ingredientsDao.save(addIngredient));
                } else {
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
    public String delete(@PathVariable long id) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == recipeDao.getOne(id).getUser().getId()) {
            // delete post
            recipeDao.deleteById(id);
        } else {
            return "redirect:/recipes";
        }
        return "redirect:/recipes";
    }

    @GetMapping("/comments/post/{id}")
    public String getPostCommentForm(@PathVariable Long id, Model model) {
        Recipe recipe = recipeDao.getOne(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("comment", new Comments());
        return "recipes/postComment";
    }

    @PostMapping("comments/post/{id}")
    public String postComment(@PathVariable long id, @RequestParam String comment, Model model) {
        Recipe recipe = recipeDao.getOne(id);
        model.addAttribute("recipe", recipe);
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "redirect:/login";
        } else if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", u);
            LocalDateTime now = LocalDateTime.now();
            Comments newComment = new Comments();
            newComment.setComment(comment);
            newComment.setCommentedAt(now);
            newComment.setUser(u);
            newComment.setRecipe(recipe);
            commentsDao.save(newComment);
            model.addAttribute("comment", newComment);
        }
        return "redirect:/recipes/" + id;
    }
}