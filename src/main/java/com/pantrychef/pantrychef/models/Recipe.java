package com.pantrychef.pantrychef.models;

import javax.persistence.*;
import java.net.UnknownServiceException;
import java.util.List;

@Entity
@Table(name="recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 25, nullable = false)
    private String ingredient;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String directions;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(

            name = "recipes_categories",
            joinColumns = {@JoinColumn(name = "recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Categories> categories;

    @ManyToMany(mappedBy = "favorites")
    private List<User> favoritedBy;

    @Column(nullable = true)
    private String recipe_path;




//    private List<Recipe> favorites;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

//    public long getUsers_id() {
//        return users_id;
//    }
//
//    public void setUsers_id(long users_id) {
//        this.users_id = users_id;
//    }

    public String getRecipe_path() {
        return recipe_path;
    }

    public void setRecipe_path(String recipe_path) {
        this.recipe_path = recipe_path;
    }

//    public List<Recipe> getFavorites() {
//        return favorites;
//    }
//
//    public void setFavorites(List<Recipe> favorites) {
//        this.favorites = favorites;
//    }
}





