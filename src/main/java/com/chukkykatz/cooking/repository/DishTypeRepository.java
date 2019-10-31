package com.chukkykatz.cooking.repository;

import com.chukkykatz.cooking.domain.DishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface DishTypeRepository extends JpaRepository<DishType, Integer> {

    @Modifying
    @Query(value = "UPDATE cooking.dishes_types SET last_advice_date = :dateTime WHERE id in (:typeIds)",
            nativeQuery = true)
    void updateLastAdviceTimeById(@Param("typeIds") List<Integer> typeIds, @Param("dateTime") Timestamp dateTime);
}