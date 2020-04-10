package com.pantrychef.pantrychef.repositories;

import com.pantrychef.pantrychef.models.ShoppingList;
import com.pantrychef.pantrychef.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingListRepo extends JpaRepository <ShoppingList, Long>{
    @Query("SELECT list FROM ShoppingList list WHERE list.id = ?1")
    ShoppingList getShoppingListByUserId(Long id);
}
