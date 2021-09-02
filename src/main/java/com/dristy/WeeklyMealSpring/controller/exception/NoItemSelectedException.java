package com.dristy.WeeklyMealSpring.controller.exception;

import com.dristy.WeeklyMealSpring.domain.Item;
import com.dristy.WeeklyMealSpring.domain.Meal;


public class NoItemSelectedException extends RuntimeException {

    private static final long serialVersionUID = 3364174363318456656L;

    public NoItemSelectedException(String message) {
        super(message);
    }
}
