package com.dristy.WeeklyMealSpring.db;

import com.dristy.WeeklyMealSpring.domain.Meal;
import com.dristy.WeeklyMealSpring.domain.Slot;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;


@Repository
public class MealRepository extends CommonRepository {

    @Transactional
    public Meal saveOrUpdate(Meal meal) {
        return super.saveOrUpdate(meal);
    }

    public List<Meal> findAll() {
        return em.createNamedQuery("Meal.fromMeal").getResultList();
    }

    public Optional<Meal> findByDayAndSlot(final DayOfWeek day, final Slot slot) {
        TypedQuery<Meal> query = em.createQuery("FROM Meal m WHERE m.day = :day AND m.slot = :slot", Meal.class)
                .setParameter("day", day)
                .setParameter("slot", slot);

        if (!query.getResultList().isEmpty()) {
            Meal meal = query.getSingleResult();
            return Optional.ofNullable(meal);
        }

        return Optional.empty();
    }

    @Transactional
    public void removeByDayAndSlot(DayOfWeek day, Slot slot) {
        Query query = em.createQuery("DELETE FROM Meal m WHERE m.day = :day AND m.slot = :slot")
                .setParameter("day", day)
                .setParameter("slot", slot);

        query.executeUpdate();
    }
}
