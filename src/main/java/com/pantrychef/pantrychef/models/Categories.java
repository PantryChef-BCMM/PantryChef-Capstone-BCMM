package com.pantrychef.pantrychef.models;

import javax.persistence.*;
import java.util.List;

@Entity
    @Table(name="categories")
public class Categories {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = ("INT(11) UNSIGNED"))
    private long id;

    @Column(length =25, nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List <Recipe> recipes;

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

}
