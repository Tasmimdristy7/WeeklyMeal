package com.dristy.WeeklyMealSpring.domain;

import com.dristy.WeeklyMealSpring.util.Util;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@NamedQuery(name = "Meal.fromMeal", query = "FROM Meal")
public class Meal extends Persistent implements Comparable<Meal> {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Slot slot;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Item> items;

    public Meal() {
        this.items = new LinkedList<>();
    }

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getItemsNames() {
        return items.isEmpty() ? "No items found." : items.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "", "."));
    }

    public String getDescription() {
        return String.format("%s, %s",
                Util.stringToTitleCase(day.toString()),
                Util.stringToTitleCase(slot.toString()));
    }

    @Override
    public int compareTo(Meal o) {
        // By using modulo operation we set Sunday as 0
        int thisDate = day.getValue() % 7;
        int otherDate = o.getDay().getValue() % 7;

        // Firstly sort by date
        if (thisDate > otherDate) {
            return 1;
        } else if (thisDate < otherDate) {
            return -1;
        } else {
            // If day is same sort by slot
            return Integer.compare(slot.getValue(), o.slot.getValue());
        }
    }
}
