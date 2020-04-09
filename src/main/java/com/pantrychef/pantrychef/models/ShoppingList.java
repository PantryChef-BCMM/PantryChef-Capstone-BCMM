package com.pantrychef.pantrychef.models;

import javax.persistence.*;

@Entity
@Table(name="shopping_list")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = ("INT(11) UNSIGNED"))
    private long id;


    //New Entity for Joiner table
    private String shoppingListItem;

    //New Entity for Joiner table
    private int quantity;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public String getShoppingListItem() {
        return shoppingListItem;
    }

    public void setShoppingListItem(String shoppingListItem) {
        this.shoppingListItem = shoppingListItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ShoppingList() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
