package com.pantrychef.pantrychef.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="ingredients")
public class Ingredient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = ("INT(11) UNSIGNED"))
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "ingredientList")
    private List<Recipe> recipeList;

//    @OneToMany(mappedBy = "ingredient")
//    private Set<ShoppingList> users_ingredient;
//
//    @OneToMany(mappedBy = "ingredient")
//    private List<RecipeIngredients> recipeIngredients;

    public Ingredient() {
    }

    public long getId() {
        return id;
    }

//    public List<RecipeIngredients> getRecipeIngredients() {
//        return recipeIngredients;
//    }
//
//    public void setRecipeIngredients(List<RecipeIngredients> recipeIngredients) {
//        this.recipeIngredients = recipeIngredients;
//    }


    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
