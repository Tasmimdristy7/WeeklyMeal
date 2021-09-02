package com.dristy.WeeklyMealSpring.service;

import com.dristy.WeeklyMealSpring.controller.data.MealUpdateData;
import com.dristy.WeeklyMealSpring.controller.exception.NoItemSelectedException;
import com.dristy.WeeklyMealSpring.db.ItemRepository;
import com.dristy.WeeklyMealSpring.db.MealRepository;
import com.dristy.WeeklyMealSpring.domain.Item;
import com.dristy.WeeklyMealSpring.domain.Meal;
import com.dristy.WeeklyMealSpring.domain.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class MealService {

    @Autowired
    MealRepository mealRepository;

    @Autowired
    ItemRepository itemRepository;

    public Meal updateMeal(MealUpdateData mealUpdateData) throws NoItemSelectedException {
        if (Objects.isNull(mealUpdateData.getItemIds())) {
            throw new NoItemSelectedException("Invalid data");
        }

        DayOfWeek dayOfWeek = mealUpdateData.getDay();
        Slot slot = mealUpdateData.getSlot();

        Optional<Meal> optionalMeal = mealRepository.findByDayAndSlot(dayOfWeek, slot);
        Meal meal = optionalMeal.orElseGet(Meal::new);

        if (!optionalMeal.isPresent()) {
            meal.setDay(dayOfWeek);
            meal.setSlot(slot);
        }

        List<Item> items = meal.getItems();
        items.clear();

        for (Integer itemId : mealUpdateData.getItemIds()) {
            Optional<Item> optionalItem = itemRepository.find(itemId);
            optionalItem.ifPresent(items::add);
        }

        meal.setItems(items);
        return mealRepository.saveOrUpdate(meal);
    }
}
