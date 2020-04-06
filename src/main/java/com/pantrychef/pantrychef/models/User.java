package com.pantrychef.pantrychef.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = ("INT(11) UNSIGNED"))
    private long id;

    @Column(length =25, nullable = false, unique = true)
    private String username;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 25, nullable = false)
    private String first_name;

    @Column(length = 25, nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAdmin;

    @Column(nullable = false)
    private String profileImageUrl;

    public User() {
    }

    //Copy Constructor
    public User(User copy){
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        first_name = copy.first_name;
        last_name = copy.last_name;
        profileImageUrl = copy.profileImageUrl;
    }

    public User(String username, String email, String first_name, String last_name, String password) {
        this.username = username;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
    }

    //Many to many  relationship connection to recipes table
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="users_favorites",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="recipe_id")}
    )
    private List<Recipe> favorites;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Recipe> recipes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comments> comments;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
//    Set<ShoppingList> shoppingLists;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}
