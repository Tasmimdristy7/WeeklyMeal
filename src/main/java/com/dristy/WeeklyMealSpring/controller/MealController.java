package com.dristy.WeeklyMealSpring.controller;

import com.dristy.WeeklyMealSpring.controller.data.MealUpdateData;
import com.dristy.WeeklyMealSpring.db.ItemRepository;
import com.dristy.WeeklyMealSpring.db.MealRepository;
import com.dristy.WeeklyMealSpring.domain.Item;
import com.dristy.WeeklyMealSpring.domain.Meal;
import com.dristy.WeeklyMealSpring.domain.Slot;
import com.dristy.WeeklyMealSpring.service.MealService;
import com.dristy.WeeklyMealSpring.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.*;


@Controller
@RequestMapping("/weekly-meal/menu")
public class MealController {

    private static final Logger log = LoggerFactory.getLogger(MealController.class);

    private static final String MENU_INDEX = "/weekly-meal/menu";

    private static final DayOfWeek[] DAY_OF_WEEKS = {DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY};

    @Autowired
    MealRepository mealRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MealService mealService;

    @GetMapping()
    public String show(ModelMap modelMap) {
        List<Meal> meals = mealRepository.findAll();
        HashMap<String, Meal> mealItemsByDay = new HashMap<>();

        meals.forEach(meal -> {
            // Creating a unique key by combining day and slot together which can be referred from JSP
            String key = String.format("%s/%s", meal.getDay(), meal.getSlot());
            mealItemsByDay.put(key, meal);
        });

        modelMap.addAttribute("meals", mealItemsByDay);
        modelMap.addAttribute("days", DAY_OF_WEEKS);
        modelMap.addAttribute("slots", Slot.values());

        return "index";
    }

    @RequestMapping("/edit/{mealDay}/{mealSlot}")
    public String edit(ModelMap modelMap,
                       RedirectAttributes redirectAttributes,
                       @PathVariable("mealDay") String mealDay,
                       @PathVariable("mealSlot") String mealSlot) {
        try {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(mealDay.toUpperCase());
            Slot slot = Slot.valueOf(mealSlot.toUpperCase());

            Map<Integer, String> itemValues = new HashMap<>();
            itemRepository.findAll().forEach(item -> itemValues.put(item.getId(), item.getName()));

            Optional<Meal> optionalMeal = mealRepository.findByDayAndSlot(dayOfWeek, slot);
            Meal meal = optionalMeal.orElseGet(Meal::new);

            MealUpdateData mealUpdateData = new MealUpdateData();
            mealUpdateData.setDay(dayOfWeek);
            mealUpdateData.setSlot(slot);
            Integer[] itemIds = meal.getItems().stream().map(Item::getId).toArray(Integer[]::new);
            mealUpdateData.setItemIds(itemIds);

            log.info(Arrays.toString(itemIds));

            modelMap.addAttribute("mealData", mealUpdateData);
            modelMap.addAttribute("actionMode", optionalMeal.isPresent() ? "Update" : "Add");
            modelMap.addAttribute("items", itemValues);

            return "meal-edit";
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());

            redirectAttributes.addFlashAttribute("meal_error", "Invalid data given. Please try again.");
            return Util.redirectTo(MENU_INDEX);
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String process(@ModelAttribute("mealData") @Valid MealUpdateData mealUpdateData,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return Util.redirectTo(String.format("/weekly-meal/menu/edit/%s/%s", mealUpdateData.getDay().toString().toLowerCase(),
                    mealUpdateData.getSlot().toString().toLowerCase()));
        }

        mealService.updateMeal(mealUpdateData);
        redirectAttributes.addFlashAttribute("meal_info", "Meal updated successfully.");

        return Util.redirectTo(MENU_INDEX);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processDeletion(@RequestParam("dayOfWeek") String dayOfWeekValue,
                                  @RequestParam("slot") String slotValue,
                                  RedirectAttributes redirectAttributes) {
        try {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayOfWeekValue.toUpperCase());
            Slot slot = Slot.valueOf(slotValue.toUpperCase());

            mealRepository.removeByDayAndSlot(dayOfWeek, slot);
            redirectAttributes.addFlashAttribute("meal_info", "Meal deleted successfully.");
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            redirectAttributes.addFlashAttribute("meal_error", "Invalid data given. Please try again.");
        }

        return Util.redirectTo(MENU_INDEX);
    }
}
