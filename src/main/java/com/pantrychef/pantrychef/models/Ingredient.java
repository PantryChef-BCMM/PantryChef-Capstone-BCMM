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
    private String ingredient_name;

    // Many to many relationship connection to recipes table
//    @ManyToMany
//    @JoinColumn(name = "ingredients")
//    private List<Recipe> recipe;

    @OneToMany(mappedBy = "ingredient")
    Set<ShoppingList> users_ingredient;

    @OneToMany(mappedBy = "ingredient")
    Set<RecipeIngredients> recipeIngredients;

    // Many to many relationship connection to recipe table
//    @ManyToMany
//    @JoinColumn(name = "recipe_ingredients")

    public Ingredient() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }
}
