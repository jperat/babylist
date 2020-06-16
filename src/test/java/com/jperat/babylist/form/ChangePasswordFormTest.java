package com.jperat.babylist.form;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChangePasswordFormTest {

    @Test
    public void testGettersAndSetters() {
        String password = "password";
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        changePasswordForm.setPassword(password);
        changePasswordForm.setRepeatedPassword(password);
        Assertions.assertEquals(password, changePasswordForm.getPassword());
        Assertions.assertEquals(password, changePasswordForm.getRepeatedPassword());
    }
}
