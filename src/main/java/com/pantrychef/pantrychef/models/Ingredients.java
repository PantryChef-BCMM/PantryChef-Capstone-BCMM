package com.pantrychef.pantrychef.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ingredients")
public class Ingredients {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String ingredient_name;

    // Many to many relationship connection to recipes table
    @ManyToMany
    @JoinColumn(name = "ingredients")
    private List<Recipe> recipe;

    // Many to many relationship connection to recipe table
//    @ManyToMany
//    @JoinColumn(name = "recipe_ingredients")

    public Ingredients() {
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
