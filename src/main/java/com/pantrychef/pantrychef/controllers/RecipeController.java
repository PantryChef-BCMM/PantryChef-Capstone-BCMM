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
        }
            if (search == null) {
                List<Recipe> recipes = recipeDao.findAll();
                model.addAttribute("recipes", recipes);
            } else if (search.length() != 0) {
                List<Recipe> recipes = recipeDao.findAll();
                List<Recipe> searchedRecipes = new ArrayList<>();
                for (Recipe recipe : recipes) {

                    if (recipe.getTitle().toLowerCase().contains(search.toLowerCase())){
                        searchedRecipes.add(recipe);
                        continue;
                    }
                    model.addAttribute("recipes", searchedRecipes);

                        String[] searchArray = search.replaceAll(", ", ",").split(",");
                        //System.out.println(Arrays.toString(searchArray));
                        ArrayList<String> ingredientArray = new ArrayList<>();
                        recipe.getIngredientList().forEach(ingredient -> {
                            ingredientArray.add(ingredient.getName());
                        });
                        //separate ingredient string into an array
                        boolean searchFlag = true;
//                        System.out.println("--------------------Next Recipe--------------------");
//                        System.out.println(recipe.getTitle());
                        for(int i = 0; i < searchArray.length; i++){
                            System.out.println(searchArray[i] + "-->" + ingredientArray.toString().toLowerCase());

                            if (!ingredientArray.toString().toLowerCase().contains(searchArray[i].toLowerCase())) {
                                System.out.println("NOPE NOT THIS ONE");
                                searchFlag = false;
                                break;
                            }
                        }
                        System.out.println("Flag: " + searchFlag);
                        if(searchFlag == true){
                            searchedRecipes.add(recipe);
                        }
                }

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
    public String updateRecipe(@ModelAttribute Recipe recipe, @PathVariable long id, @RequestParam(name = "ingredient-param") List<String> ingredientsStringList, @RequestParam List<Categories> categories) {
        Recipe recipeToEdit = recipeDao.getOne(id);
        recipeToEdit.setTitle(recipeToEdit.getTitle());
//        recipeToEdit.setDirections(recipeToEdit.getDirections());
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

    public String createRecipe(@RequestParam(name = "ingredient-param") List<String> ingredientsStringList, @RequestParam(name = "instruction-param") List<String> instructionsStringList, @RequestParam String title, @RequestParam List<Categories> categories) {
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
//        recipe.setDirections(directions);
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

        ////////////////

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