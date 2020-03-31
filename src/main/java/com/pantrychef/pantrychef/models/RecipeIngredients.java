package com.pantrychef.pantrychef.models;


import javax.persistence.*;

@Entity
@Table(name="recipe_ingredients")
public class RecipeIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = ("INT(11) UNSIGNED"))
    private long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;

    //New Entity for Joiner table
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
