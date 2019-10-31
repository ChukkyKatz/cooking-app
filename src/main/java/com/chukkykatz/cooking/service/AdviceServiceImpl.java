package com.chukkykatz.cooking.service;

import com.chukkykatz.cooking.domain.Dish;
import com.chukkykatz.cooking.domain.DishType;
import com.chukkykatz.cooking.domain.Receipt;
import com.chukkykatz.cooking.repository.DishRepository;
import com.chukkykatz.cooking.repository.DishTypeRepository;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

@Service
public class AdviceServiceImpl implements AdviceService {

    private DishRepository dishRepository;
    private DishTypeRepository dishTypeRepository;

    @Autowired
    public AdviceServiceImpl(final DishRepository dishRepository,
                             final DishTypeRepository dishTypeRepository
    ) {
        this.dishRepository = dishRepository;
        this.dishTypeRepository = dishTypeRepository;
    }

    @Override
    @Transactional
    public Map<Dish, Receipt> getAdvice() {
        final List<Integer> typesToAdvice = checkExpiration();
        final Map<Dish, Receipt> advice = new HashMap<>(typesToAdvice.size());
        typesToAdvice.forEach(type -> {
            final Dish dish = dishRepository.getRandomByDishType(type);
            advice.put(dish, dish.getRandomReceipt());
        });
        if (typesToAdvice.size() != 0) {
            updateAdviceTimestamps(typesToAdvice);
        }
        return advice;
    }

    private List<Integer> checkExpiration() {
        final List<DishType> allTypesList = dishTypeRepository.findAll();
        final List<Integer> scheduledTypes = new LinkedList<>();
        allTypesList.forEach(type -> {
            final Date lastAdviseDate = type.getLastAdviceDate();
            try {
                final CronExpression cronExpression = new CronExpression(type.getAdviceSchedule());
                final Date nextExecutionDate = cronExpression.getNextValidTimeAfter(lastAdviseDate);
                final Date currentDate = new Date(System.currentTimeMillis());
                boolean isScheduled = nextExecutionDate.before(currentDate);
                if (isScheduled) {
                    scheduledTypes.add(type.getId());
                }
            } catch (ParseException e) {
                System.out.println("Fail to parse schedule expression - " + type.getAdviceSchedule());
            }
        });
        return scheduledTypes;
    }

    private void updateAdviceTimestamps(List<Integer> dishTypes) {
        dishTypeRepository.updateLastAdviceTimeById(dishTypes, new Timestamp(new Date().getTime()));
    }
}
