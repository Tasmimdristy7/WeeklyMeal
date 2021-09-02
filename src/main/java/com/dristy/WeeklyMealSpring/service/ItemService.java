package com.dristy.WeeklyMealSpring.service;

import com.dristy.WeeklyMealSpring.db.ItemRepository;
import com.dristy.WeeklyMealSpring.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Transactional
    public void removeItem(int itemId) throws IllegalArgumentException, IllegalStateException {
        Optional<Item> optionalItem = itemRepository.find(itemId);

        if (!optionalItem.isPresent()) {
            throw new IllegalArgumentException(String.format("Item with id %s doesn't exist.", itemId));
        }

        Item item = optionalItem.get();

        if (!item.getMeals().isEmpty()) {
            throw new IllegalStateException("Item is used in meals.");
        }

        itemRepository.remove(item);
    }
}
