package com.dristy.WeeklyMealSpring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Item extends Persistent implements Comparable<Item> {

    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @ManyToMany(mappedBy = "items")
    private List<Meal> meals;

    public Item() {
        this.meals = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        } else if (obj == this) {
            return true;
        }

        if (obj instanceof Item) {
            Item otherItem = (Item) obj;

            return Objects.nonNull(otherItem.getId()) && otherItem.getId().equals(getId());
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Item o) {
        return Integer.compare(o.getId(), getId());
    }

    @Override
    public String toString() {
        return name;
    }
}
