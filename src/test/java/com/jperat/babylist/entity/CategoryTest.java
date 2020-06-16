package com.jperat.babylist.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

public class CategoryTest {

    @Test
    public void testGettersAndSetters() {
        Integer id = 566;
        String name = "name";
        Collection<Item> items = new ArrayList<Item>();
        Category category = new Category();
        category.setId(id);
        Assertions.assertEquals(id, category.getId());
        category.setName(name);
        Assertions.assertEquals(name, category.getName());
        category.setItems(items);
        Assertions.assertEquals(items, category.getItems());
    }
}
