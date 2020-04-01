package com.pantrychef.pantrychef.models;

import javax.persistence.*;

@Entity
@Table(name="shopping_list")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = ("INT(11) UNSIGNED"))
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;


    //New Entity for Joiner table
    private String shoppingListItem;

    //New Entity for Joiner table
    private int quantity;

    public ShoppingList() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getShoppingListItem() {
        return shoppingListItem;
    }

    public void setShoppingListItem(String shoppingListItem) {
        this.shoppingListItem = shoppingListItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
