package com.jperat.babylist.constraint;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*\\-])(?=\\S+$).{8,32}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.matches(PATTERN);
    }

    @Override
    public void initialize(Password constraintAnnotation) {

    }
}
