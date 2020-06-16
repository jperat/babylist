package com.jperat.babylist.form;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResetPasswordFormTest {

    @Test
    public void testGettersAndSetters() {
        ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
        String email = "test@test.test";
        String password = "password";
        String token = "token";
        resetPasswordForm.setEmail(email);
        resetPasswordForm.setPassword(password);
        resetPasswordForm.setRepeatedPassword(password);
        resetPasswordForm.setToken(token);
        Assertions.assertEquals(email, resetPasswordForm.getEmail());
        Assertions.assertEquals(password, resetPasswordForm.getPassword());
        Assertions.assertEquals(password, resetPasswordForm.getRepeatedPassword());
        Assertions.assertEquals(token, resetPasswordForm.getToken());
    }
}
