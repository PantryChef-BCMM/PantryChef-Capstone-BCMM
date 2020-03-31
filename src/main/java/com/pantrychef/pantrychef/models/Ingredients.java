//package com.pantrychef.pantrychef.models;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name="ingredients")
//public class Ingredients {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @Column(nullable = false)
//    private String ingredient_name;
//
//    @ManyToMany
//    @JoinColumn(name = "user_shop_list_id")
//    private   user_shop_list;
//
//    public Ingredients() {
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getIngredient_name() {
//        return ingredient_name;
//    }
//
//    public void setIngredient_name(String ingredient_name) {
//        this.ingredient_name = ingredient_name;
//    }
//}
