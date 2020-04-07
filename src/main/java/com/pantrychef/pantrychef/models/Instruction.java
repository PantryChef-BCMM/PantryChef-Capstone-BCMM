package com.pantrychef.pantrychef.models;


import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="instructions")
public class Instruction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = ("INT(11) UNSIGNED"))
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "instructionList")
    private List<Recipe> recipeList;

    public long getId() {
        return id;
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

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }
}
