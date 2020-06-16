package com.jperat.babylist.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void testGettersAndSetters() {
        Integer id = 12;
        Float price = 1.1f;
        String url = "http://127.0.0.1:8000";
        Item item = new Item();

        Availability availability1 = new Availability();

        availability1.setId(id);
        Assertions.assertEquals(id, availability1.getId());

        availability1.setPrice(price);
        Assertions.assertEquals(price, availability1.getPrice());

        availability1.setUrl(url);
        Assertions.assertEquals(url, availability1.getUrl());

        availability1.setItem(item);
        Assertions.assertEquals(item, availability1.getItem());
    }

}
