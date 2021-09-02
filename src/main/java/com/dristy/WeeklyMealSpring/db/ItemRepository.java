package com.dristy.WeeklyMealSpring.db;

import com.dristy.WeeklyMealSpring.domain.Item;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public class ItemRepository extends CommonRepository {

    @Transactional
    public Item saveOrUpdate(Item item) {
        return super.saveOrUpdate(item);
    }

    public List<Item> findAll() {
        return em.createQuery("FROM Item ").getResultList();
    }

    public Optional<Item> find(int itemId) {
        return super.find(Item.class, itemId);
    }

    @Transactional
    public void remove(Item item) {
        super.remove(item);
    }
}
