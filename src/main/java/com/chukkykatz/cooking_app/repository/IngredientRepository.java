package com.chukkykatz.cooking_app.repository;

import com.chukkykatz.cooking_app.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
