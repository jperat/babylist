package com.jperat.babylist.constraint;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import javax.validation.ConstraintValidatorContext;

public class DuplicateFieldValidatorTest {

    @Test
    public void testIsValid() {
        ConstraintValidatorContext constraintValidatorContext = Mockito.mock(ConstraintValidatorContext.class);
        DuplicateFieldValidator duplicateFieldValidator = new DuplicateFieldValidator();
        DuplicateField duplicateField = Mockito.mock(DuplicateField.class);
        String [] getters = {"getPassword", "getRepeatedPassword"};
        when(duplicateField.getters()).thenReturn(getters);
        class ValidTest {
            public String getPassword() {
                return "test";
            }

            public String getRepeatedPassword() {
                return "test";
            }
        }
        duplicateFieldValidator.initialize(duplicateField);
        Assertions.assertEquals(true, duplicateFieldValidator.isValid(new ValidTest(), constraintValidatorContext));
    }
}
