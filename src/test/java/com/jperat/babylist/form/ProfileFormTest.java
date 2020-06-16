package com.jperat.babylist.form;

import com.jperat.babylist.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProfileFormTest {

    @Test
    public void testGettersAndSetters() {
        ProfileForm profileForm = new ProfileForm();
        profileForm.setFirstname("firstname");
        profileForm.setLastname("lastname");
        Assertions.assertEquals("firstname", profileForm.getFirstname());
        Assertions.assertEquals("lastname", profileForm.getLastname());
    }

    @Test
    public void testConstructor() {
        ProfileForm profileForm = new ProfileForm();
        Assertions.assertNull( profileForm.getFirstname());
        Assertions.assertNull( profileForm.getLastname());

        User user = new User();
        user.setLastname("Lastname");
        user.setFirstname("Firstname");
        profileForm = new ProfileForm(user);
        Assertions.assertEquals("Firstname", profileForm.getFirstname());
        Assertions.assertEquals("Lastname", profileForm.getLastname());
    }
}
