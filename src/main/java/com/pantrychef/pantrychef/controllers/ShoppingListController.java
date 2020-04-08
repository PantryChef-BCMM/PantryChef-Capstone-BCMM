package com.pantrychef.pantrychef.controllers;

import com.pantrychef.pantrychef.models.ShoppingList;
import com.pantrychef.pantrychef.models.User;
import com.pantrychef.pantrychef.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShoppingListController {
    private RecipeRepo recipeDao;
    private UserRepo userDao;
    private IngredientsRepo ingredientsDao;
    private CategoriesRepo categoriesDao;
    private CommentsRepo commentsDao;
    @Value("${filestack.api.key}")
    private String fsapi;

    public ShoppingListController(RecipeRepo recipeDao, UserRepo userDao, IngredientsRepo ingredientsDao, CategoriesRepo categoriesDao, CommentsRepo commentsDao) {
        this.recipeDao = recipeDao;
        this.userDao = userDao;
        this.ingredientsDao = ingredientsDao;
        this.categoriesDao = categoriesDao;
        this.commentsDao = commentsDao;
    }

    @GetMapping("/list/{id}")
    public String getShoppingList(@PathVariable long id, Model model){
        User user = userDao.getOne(id);
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setUser(user);
        model.addAttribute("user", user);
        model.addAttribute("shoppingList", shoppingList);
        return "users/shoppingList";
    }
}
