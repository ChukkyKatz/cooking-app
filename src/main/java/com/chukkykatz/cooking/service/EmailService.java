package com.chukkykatz.cooking.service;

import com.chukkykatz.cooking.domain.Dish;
import com.chukkykatz.cooking.domain.Receipt;

import java.util.Map;

public interface EmailService {

    void sendAdviceMessage(Map<Dish, Receipt> types);
}
