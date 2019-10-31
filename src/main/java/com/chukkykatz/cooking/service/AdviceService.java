package com.chukkykatz.cooking.service;

import com.chukkykatz.cooking.domain.Dish;
import com.chukkykatz.cooking.domain.Receipt;

import java.util.Map;

public interface AdviceService {

    Map<Dish, Receipt> getAdvice();
}
