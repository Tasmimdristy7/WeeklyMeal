package com.dristy.WeeklyMealSpring.controller.data;

import com.dristy.WeeklyMealSpring.domain.Slot;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;


public class MealUpdateData {

    @NotNull(message = "Please select a day for the meal.")
    private DayOfWeek day;

    @NotNull(message = "Please select a slot for the meal.")
    private Slot slot;

    @NotEmpty(message = "Please select at least one item for the meal.")
    private Integer[] itemIds;

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Integer[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(Integer[] itemIds) {
        this.itemIds = itemIds;
    }
}
