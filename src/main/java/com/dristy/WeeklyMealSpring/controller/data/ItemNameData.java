package com.dristy.WeeklyMealSpring.controller.data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class ItemNameData {

    @NotNull
    @Size(min = 3, max = 100, message = "Item name should be between 3 and 100 characters.")
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
