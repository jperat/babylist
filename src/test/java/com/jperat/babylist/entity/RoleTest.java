package com.jperat.babylist.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RoleTest {

    @Test
    public void testGettersAndSetters() {
        Integer id = 12;
        String roleStr = "roles";
        ArrayList<User> users = new ArrayList<User>();

        Role role = new Role();

        role.setId(id);
        Assertions.assertEquals(id, role.getId());

        role.setRole(roleStr);
        Assertions.assertEquals(roleStr, role.getRole());

        role.setUsers(users);
        Assertions.assertEquals(users, role.getUsers());

        role = new Role(roleStr);
        Assertions.assertEquals(roleStr, role.getRole());
    }
}
