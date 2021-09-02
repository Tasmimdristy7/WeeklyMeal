package com.dristy.WeeklyMealSpring.controller;

import com.dristy.WeeklyMealSpring.controller.data.ItemNameData;
import com.dristy.WeeklyMealSpring.db.ItemRepository;
import com.dristy.WeeklyMealSpring.domain.Item;
import com.dristy.WeeklyMealSpring.service.ItemService;
import com.dristy.WeeklyMealSpring.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/weekly-meal/items")
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    private static final String ITEMS_INDEX = "/weekly-meal/items";

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @RequestMapping
    public String show(ModelMap modelMap) {
        modelMap.addAttribute("items", itemRepository.findAll());
        modelMap.addAttribute("itemData", new ItemNameData());

        return "items";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processDeletion(@RequestParam("itemId") String itemIdValue,
                                  RedirectAttributes redirectAttributes) {
        try {
            int itemId = Integer.parseInt(itemIdValue);

            itemService.removeItem(itemId);
            redirectAttributes.addFlashAttribute("item_info", "Item deleted successfully.");
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            redirectAttributes.addFlashAttribute("item_error", "Cann't delete item. Item doesn't exist.");
        } catch (IllegalStateException e) {
            log.warn(e.getMessage());
            redirectAttributes.addFlashAttribute("item_error", "Cann't delete item. Item is used in meals.");
        }

        return Util.redirectTo(ITEMS_INDEX);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processInsertion(@ModelAttribute("itemData") @Valid ItemNameData itemNameData,
                                   BindingResult bindingResult,
                                   ModelMap modelMap,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("items", itemRepository.findAll());
            return "items";
        }

        log.info("Process");

        String itemName = Util.stringToTitleCase(itemNameData.getItemName());
        Item item = new Item();
        item.setName(itemName);

        itemRepository.saveOrUpdate(item);
        redirectAttributes.addFlashAttribute("item_info", "Item added successfully.");
        return Util.redirectTo(ITEMS_INDEX);
    }
}
