package com.pantrychef.pantrychef.repositories;

import com.pantrychef.pantrychef.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {
    Recipe findByTitle(String Title);
    Recipe findByUserId(long id);
}
