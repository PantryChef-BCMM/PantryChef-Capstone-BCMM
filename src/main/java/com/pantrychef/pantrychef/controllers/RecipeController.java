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

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {

    private RecipeRepo recipeDao;
    private UserRepo userDao;
    private IngredientsRepo ingredientsDao;
    private InstructionsRepo instructionsDao;
    private CategoriesRepo categoriesDao;
    private CommentsRepo commentsDao;
    @Value("${filestack.api.key}")
    private String fsapi;
    @Value("${spoonacular.api.key}")
    private String sapi;


    public RecipeController(RecipeRepo recipeDao, UserRepo userDao, IngredientsRepo ingredientsDao, InstructionsRepo instructionsDao, CategoriesRepo categoriesDao, CommentsRepo commentsDao) {

        this.recipeDao = recipeDao;
        this.userDao = userDao;
        this.ingredientsDao = ingredientsDao;
        this.instructionsDao = instructionsDao;
        this.categoriesDao = categoriesDao;
        this.commentsDao = commentsDao;
    }

    @GetMapping("/recipes")
    public String getPosts(Model model, @RequestParam(required = false) String search, @RequestParam(required = false, name = "categories") Long value) {
        //=== SEARCH BAR ===//
        model.addAttribute("search", search);
        model.addAttribute("value", value);
        model.addAttribute("sapi", sapi);

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", u);
        }
        if (search == null && value == null) {
            List<Recipe> recipes = recipeDao.findAll();
            model.addAttribute("recipes", recipes);
        } else {
            List<Recipe> recipes = recipeDao.findAll();
            List<Recipe> searchedRecipes = new ArrayList<>();

            for (Recipe recipe : recipes) {
                if (value != null) {
                    boolean valueFlag = false;
                    for (Categories category : recipe.getCategories()) {
                        if (category.getId() == value) {
                            System.out.println(value + " --> =? " + category.getId());

                            searchedRecipes.add(recipe);
                            valueFlag = true;
                            break;
                        }
                    }
                    if (valueFlag) {
                        continue;
                    }
                }
                if (search != null) {
                    if (recipe.getTitle().toLowerCase().contains(search.toLowerCase())) {
                        searchedRecipes.add(recipe);
                        continue;
                    }
                    String[] searchArray = search.replaceAll(", ", ",").split(",");
                    ArrayList<String> ingredientArray = new ArrayList<>();

                    recipe.getIngredientList().forEach(ingredient -> {
                        ingredientArray.add(ingredient.getName());
                    });
                    //separate ingredient string into an array
                    boolean searchFlag = true;
                    for (String s : searchArray) {
                        if (!ingredientArray.toString().toLowerCase().contains(s.toLowerCase())) {
                            searchFlag = false;
                            break;
                        }
                    }
                    if (searchFlag) {
                        searchedRecipes.add(recipe);
                    }
                }
            }
            model.addAttribute("recipes", searchedRecipes);

        }
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
    public String updateRecipe(@ModelAttribute Recipe recipe, @PathVariable long id, @RequestParam(name = "recipeImageUrl") String recipeImageUrl, @RequestParam(name = "ingredient-param") List<String> ingredientsStringList, @RequestParam(name = "instruction-param") List<String> instructionsStringList, @RequestParam List<Categories> categories) {
        Recipe recipeToEdit = recipeDao.getOne(id);
        User user = recipeDao.getOne(id).getUser();
        recipeToEdit.setTitle(recipeToEdit.getTitle());
//        recipeToEdit.setDirections(recipeToEdit.getDirections());
//        recipe.setRecipeImageUrl("https://picsum.photos/200");
        recipe.setRecipeImageUrl(recipeImageUrl);
        recipe.setCategories(categories);

        //////////////// Instructions ////////////////

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
        recipe.setUser(user);
        recipeDao.save(recipe);

        //////////////// Instructions ////////////////

        List<Instruction> recipeInstructionList = new ArrayList<>();

        for (String instruction : instructionsStringList) {
            if (instruction != "") {
                if (instructionsDao.findInstructionByname(instruction) == null) {
                    Instruction addInstruction = new Instruction();
                    addInstruction.setName(instruction);
                    List<Recipe> recipeList = new ArrayList<>();
                    recipeList.add(recipe);
                    addInstruction.setRecipeList(recipeList);
                    recipeInstructionList.add(instructionsDao.save(addInstruction));
                } else {
                    Instruction updateInstruction = instructionsDao.findInstructionByname(instruction);
                    List<Recipe> recipeList = updateInstruction.getRecipeList();
                    recipeList.add(recipe);
                    updateInstruction.setRecipeList(recipeList);
                    recipeInstructionList.add(instructionsDao.save(updateInstruction));

                }
            }
        }
        recipe.setInstructionList(recipeInstructionList);
        recipe.setUser(user);
        recipeDao.save(recipe);

        ////////////////////////////////////////

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
    public String createRecipe(@RequestParam(name = "recipeImageUrl") String recipeImageUrl, @RequestParam(name = "ingredient-param") List<String> ingredientsStringList, @RequestParam(name = "instruction-param") List<String> instructionsStringList, @RequestParam String title, @RequestParam List<Categories> categories) {
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
//        recipe.setDirections(directions);
//        recipe.setRecipeImageUrl("https://picsum.photos/200"); //
        recipe.setRecipeImageUrl(recipeImageUrl); //
        recipe.setCategories(categories);
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setUser(u);
        recipeDao.save(recipe);

        //////////////// Ingredients ////////////////

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

        //////////////// Instructions ////////////////

        List<Instruction> recipeInstructionList = new ArrayList<>();

        for (String instruction : instructionsStringList) {
            if (instruction != "") {
                if (instructionsDao.findInstructionByname(instruction) == null) {
                    Instruction addInstruction = new Instruction();
                    addInstruction.setName(instruction);
                    List<Recipe> recipeList = new ArrayList<>();
                    recipeList.add(recipe);
                    addInstruction.setRecipeList(recipeList);
                    recipeInstructionList.add(instructionsDao.save(addInstruction));
                } else {
                    Instruction updateInstruction = instructionsDao.findInstructionByname(instruction);
                    List<Recipe> recipeList = updateInstruction.getRecipeList();
                    recipeList.add(recipe);
                    updateInstruction.setRecipeList(recipeList);
                    recipeInstructionList.add(instructionsDao.save(updateInstruction));

                }
            }
        }
        recipe.setInstructionList(recipeInstructionList);
        recipeDao.save(recipe);

        ////////////////////////////////////////

        return "redirect:/recipes";
    }

    //Delete a recipe post
    @PostMapping("/recipe/{id}/delete")
    public String delete(@PathVariable long id) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getId() == recipeDao.getOne(id).getUser().getId() || loggedInUser.isAdmin()) {
            // delete post
            recipeDao.deleteById(id);
        } else {
            return "redirect:/recipes";
        }
        return "redirect:/recipes";
    }


}