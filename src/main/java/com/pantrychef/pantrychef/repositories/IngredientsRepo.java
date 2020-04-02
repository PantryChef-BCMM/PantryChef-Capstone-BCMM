package com.pantrychef.pantrychef.repositories;

import com.pantrychef.pantrychef.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IngredientsRepo extends JpaRepository<Ingredient, Long> {
}
