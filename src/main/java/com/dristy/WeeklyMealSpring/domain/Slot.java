package com.dristy.WeeklyMealSpring.domain;


public enum Slot {

    BREAKFAST(0), LUNCH(1);

    private final int value;

    Slot(int value) {
        this.value = value;
    }

    public static Slot from(int index) {
        switch (index) {
            case 0:
                return Slot.BREAKFAST;
            case 1:
                return Slot.LUNCH;
            default:
                throw new IllegalArgumentException("Wrong index given.");
        }
    }

    int getValue() {
        return value;
    }
}
