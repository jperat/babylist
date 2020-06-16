package com.jperat.babylist.form;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ForgotPasswordFormTest {

    @Test
    public void testGettersAndSetters() {
        String email = "test@test.test";
        ForgotPasswordForm forgotPasswordForm = new ForgotPasswordForm();
        forgotPasswordForm.setEmail(email);
        Assertions.assertEquals(email, forgotPasswordForm.getEmail());
    }
}
