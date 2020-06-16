package com.jperat.babylist.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class UserTest {

    @Test
    public void testGettersAndSetters() {
        Integer id = 4;
        String firstname = "prénom";
        String lastname = "nom";
        String email = "email@email.com";
        String password = "password";
        Collection<Role> roles = new ArrayList<Role>();
        Collection<UserItem> userItems = new ArrayList<UserItem>();
        String token = "token";
        Date date = new Date();

        User user = new User();

        user.setId(id);
        Assertions.assertEquals(id, user.getId());

        user.setFirstname(firstname);
        Assertions.assertEquals(firstname, user.getFirstname());

        user.setLastname(lastname);
        Assertions.assertEquals(lastname, user.getLastname());

        user.setEmail(email);
        Assertions.assertEquals(email, user.getEmail());

        user.setPassword(password);
        Assertions.assertEquals(password, user.getPassword());

        user.setRoles(roles);
        Assertions.assertEquals(roles, user.getRoles());

        user.setUserItems(userItems);
        Assertions.assertEquals(userItems, user.getUserItems());

        user.setToken(token);
        Assertions.assertEquals(token, user.getToken());

        user.setTokenExpiryDate(date);
        Assertions.assertEquals(date, user.getTokenExpiryDate());

        user.setEnable(true);
        Assertions.assertTrue(user.isEnable());
    }

    @Test
    public void testAddRole() {
        Role role = new Role();
        User user = new User();
        user.addRole(role);
        Assertions.assertTrue(user.getRoles().contains(role));
    }

    @Test
    public void testAddItem() {
        UserItem userItem = new UserItem();
        User user = new User();
        user.addUserItem(userItem);
        Assertions.assertTrue(user.getUserItems().contains(userItem));
    }

}
