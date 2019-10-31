package com.chukkykatz.cooking.controller;

import com.chukkykatz.cooking.domain.Dish;
import com.chukkykatz.cooking.domain.DishType;
import com.chukkykatz.cooking.domain.Ingredient;
import com.chukkykatz.cooking.domain.Receipt;
import com.chukkykatz.cooking.repository.DishRepository;
import com.chukkykatz.cooking.repository.DishTypeRepository;
import com.chukkykatz.cooking.repository.IngredientRepository;
import com.chukkykatz.cooking.repository.ReceiptRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Slf4j
@Controller
public class CookingController {

    private DishRepository dishRepository;
    private DishTypeRepository dishTypeRepository;
    private IngredientRepository ingredientRepository;
    private ReceiptRepository receiptRepository;

    @Autowired
    public CookingController(final DishRepository dishRepository,
                             final DishTypeRepository dishTypeRepository,
                             final IngredientRepository ingredientRepository,
                             final ReceiptRepository receiptRepository
    ) {
        this.dishRepository = dishRepository;
        this.dishTypeRepository = dishTypeRepository;
        this.ingredientRepository = ingredientRepository;
        this.receiptRepository = receiptRepository;
    }

    @GetMapping("/")
    public RedirectView index() {
        return new RedirectView("/statistics");
    }

    @GetMapping("/statistics")
    public String statistics(final Model model) {
        model.addAttribute("dishesTypesNumber", dishTypeRepository.count());
        model.addAttribute("dishesNumber", dishRepository.count());
        model.addAttribute("ingredientsNumber", ingredientRepository.count());
        model.addAttribute("receiptsNumber", receiptRepository.count());
        return "statistics";
    }

    @GetMapping("/overview")
    public String overview(final Model model) {
        final List<Dish> allDishes = new ArrayList<>(
                dishRepository.findAll(new Sort(Sort.Direction.ASC, "name"))
        );
        model.addAttribute("allDishes", allDishes);
        return "overview";
    }

    @GetMapping("/add")
    public String add(final Model model) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("dishType", new DishType());
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("receipt", new Receipt());
        final List<DishType> allTypes = new ArrayList<>(
                dishTypeRepository.findAll(new Sort(Sort.Direction.ASC, "typeName"))
        );
        final List<Ingredient> allIngredients = new ArrayList<>(
                ingredientRepository.findAll(new Sort(Sort.Direction.ASC, "name"))
        );
        final List<Dish> allDishes = new ArrayList<>(
                dishRepository.findAll(new Sort(Sort.Direction.ASC, "name"))
        );
        model.addAttribute("allTypes", allTypes);
        model.addAttribute("allIngredients", allIngredients);
        model.addAttribute("allDishes", allDishes);
        return "add";
    }

    @PostMapping("/add-dish")
    public RedirectView addDish(@ModelAttribute final Dish dish) {
        final Integer dishTypeId = dish.getDishType().getId();
        dish.setDishType(dishTypeRepository.getOne(dishTypeId));
        dishRepository.save(dish);
        return new RedirectView("/statistics");
    }

    @PostMapping("/add-receipt")
    public RedirectView addReceipt(@ModelAttribute final Receipt receipt) {
        receiptRepository.save(receipt);
        return new RedirectView("/statistics");
    }

    @PostMapping("/add-ingredient")
    public RedirectView addIngredient(@ModelAttribute final Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return new RedirectView("/statistics");
    }
}
