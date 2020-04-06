//package com.pantrychef.pantrychef.controllers;
//
//import com.pantrychef.pantrychef.models.Comments;
//import com.pantrychef.pantrychef.models.Recipe;
//import com.pantrychef.pantrychef.models.User;
//import com.pantrychef.pantrychef.repositories.*;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.time.LocalDateTime;
//
//@Controller
//public class CommentsController {
//    private RecipeRepo recipeDao;
//    private UserRepo userDao;
//    private RecipeIngredientsRepo recipeIngredientsDao;
//    private IngredientsRepo ingredientsDao;
//    private CategoriesRepo categoriesDao;
//    private CommentsRepo commentsDao;
//
//    public CommentsController(RecipeRepo recipeDao, UserRepo userDao, RecipeIngredientsRepo recipeIngredientsDao, IngredientsRepo ingredientsDao, CategoriesRepo categoriesDao, CommentsRepo commentsDao) {
//        this.recipeDao = recipeDao;
//        this.userDao = userDao;
//        this.recipeIngredientsDao = recipeIngredientsDao;
//        this.ingredientsDao = ingredientsDao;
//        this.categoriesDao = categoriesDao;
//        this.commentsDao = commentsDao;
//    }
//
//
//        @GetMapping("/comments/post/{id}")
//    public String getPostCommentForm(@PathVariable Long id, Model model){
//        Recipe recipe = recipeDao.getOne(id);
//        model.addAttribute("recipe", recipe);
//        model.addAttribute("comment", new Comments());
//        return "recipes/postComment";
//    }
//
//    @PostMapping("comments/post/{id}")
//    public String postComment(@PathVariable long id, @RequestParam String comment, Model model){
//         Recipe recipe = recipeDao.getOne(id);
//         model.addAttribute("recipe", recipe);
//        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser"){
//            User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            model.addAttribute("user", u);
//            LocalDateTime now = LocalDateTime.now();
//            Comments newComment = new Comments();
//            newComment.setComment(comment);
//            newComment.setCommentedAt(now);
//            newComment.setId(u.getId());
//            commentsDao.save(newComment);
//            model.addAttribute("comment", newComment);
//
//        }else{
//            return "Redirect:/login";
//        }
//        return "redirect:/recipes";
//    }
//}
