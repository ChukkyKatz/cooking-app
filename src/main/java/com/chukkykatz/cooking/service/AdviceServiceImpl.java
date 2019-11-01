package com.chukkykatz.cooking.service;

import com.chukkykatz.cooking.domain.Dish;
import com.chukkykatz.cooking.domain.DishType;
import com.chukkykatz.cooking.domain.Receipt;
import com.chukkykatz.cooking.repository.DishRepository;
import com.chukkykatz.cooking.repository.DishTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
        final List<DishType> scheduledTypes = checkExpiration();
        final Map<Dish, Receipt> advice = new HashMap<>(scheduledTypes.size());
        scheduledTypes.forEach(type -> {
            final Dish dish = dishRepository.getRandomByDishType(type.getId());
            if (dish == null) {
                log.warn("No dishes found for type " + type.getTypeName() + ", skipped");
            } else {
                final Receipt receipt = dish.getRandomReceipt();
                if (receipt == null) {
                    log.warn("No receipts found for dish " + dish.getName() + ", skipped");
                } else {
                    advice.put(dish, dish.getRandomReceipt());
                }
            }
        });
        if (scheduledTypes.size() != 0) {
            updateAdviceTimestamps(scheduledTypes);
        }
        return advice;
    }

    private List<DishType> checkExpiration() {
        final List<DishType> allTypesList = dishTypeRepository.findAll();
        final List<DishType> scheduledTypes = new LinkedList<>();
        allTypesList.forEach(type -> {
            final Date lastAdviseDate = type.getLastAdviceDate();
            try {
                final CronExpression cronExpression = new CronExpression(type.getAdviceSchedule());
                final Date nextExecutionDate = cronExpression.getNextValidTimeAfter(lastAdviseDate);
                final Date currentDate = new Date(System.currentTimeMillis());
                boolean isScheduled = nextExecutionDate.before(currentDate);
                if (isScheduled) {
                    scheduledTypes.add(type);
                }
            } catch (ParseException e) {
                log.error("Fail to parse schedule expression - " + type.getAdviceSchedule(), e);
            }
        });
        return scheduledTypes;
    }

    private void updateAdviceTimestamps(List<DishType> scheduledTypes) {
        final List<Integer> dishTypesIdsToUpdate = scheduledTypes.stream()
                                                                 .map(DishType::getId)
                                                                 .collect(Collectors.toList());
        dishTypeRepository.updateLastAdviceTimeById(dishTypesIdsToUpdate, new Timestamp(new Date().getTime()));
    }
}
