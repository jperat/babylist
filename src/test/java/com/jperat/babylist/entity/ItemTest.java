package com.jperat.babylist.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

public class ItemTest {

    @Test
    public void testGettersAndSetters() {
        Integer id = 123;
        String name = "name";
        User user = new User();
        boolean enable = true;
        Integer quantity = 2;
        Collection<Availability> availabilities = new ArrayList<Availability>();
        Category category = new Category();
        Collection<UserItem> userItems = new ArrayList<UserItem>();

        Item item = new Item();

        item.setId(id);
        Assertions.assertEquals(id, item.getId());

        item.setName(name);
        Assertions.assertEquals(name, item.getName());

        item.setEnable(enable);
        Assertions.assertEquals(enable, item.isEnable());

        item.setQuantity(quantity);
        Assertions.assertEquals(quantity, item.getQuantity());

        item.setAvailabilities(availabilities);
        Assertions.assertEquals(availabilities, item.getAvailabilities());

        item.setCategory(category);
        Assertions.assertEquals(category, item.getCategory());

        item.setUserItems(userItems);
        Assertions.assertEquals(userItems, item.getUserItems());
    }
}
