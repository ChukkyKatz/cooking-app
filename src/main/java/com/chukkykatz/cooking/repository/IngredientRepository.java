package com.chukkykatz.cooking.repository;

import com.chukkykatz.cooking.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
