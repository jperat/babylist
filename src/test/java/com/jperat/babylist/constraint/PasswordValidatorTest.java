package com.jperat.babylist.constraint;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

public class PasswordValidatorTest {

    @Test
    public void testIsValid() {
        ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
        PasswordValidator passwordValidator = new PasswordValidator();
        String password = "F@rd1coSports";
        Assertions.assertEquals(true, passwordValidator.isValid(password, constraintValidatorContext));
        Assertions.assertEquals(false, passwordValidator.isValid("", constraintValidatorContext));
    }

}
