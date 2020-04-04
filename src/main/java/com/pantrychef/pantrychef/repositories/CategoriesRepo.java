package com.pantrychef.pantrychef.repositories;

import com.pantrychef.pantrychef.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepo extends JpaRepository<Categories, Long> {
}
