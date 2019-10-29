package com.chukkykatz.cooking_app.service;

import com.chukkykatz.cooking_app.domain.Dish;
import com.chukkykatz.cooking_app.domain.Receipt;

import java.util.Map;

public interface EmailService {

    void sendAdviceMessage(Map<Dish, Receipt> types);
}
