package com.chukkykatz.cooking.repository;

import com.chukkykatz.cooking.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query(value = "select * from cooking.dishes where type = :dishType" +
            " offset floor(random() * (select count(*) from cooking.dishes where type = :dishType)) limit 1",
            nativeQuery = true)
    Dish getRandomByDishType(@Param("dishType") Integer dishType);
}
