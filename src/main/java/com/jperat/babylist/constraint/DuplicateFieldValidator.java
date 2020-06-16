package com.jperat.babylist.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DuplicateFieldValidator implements ConstraintValidator<DuplicateField, Object> {

    private String[] getters;

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String value = getValue(o, getters[0]);
        for (String getter: getters) {
            if (value != getValue(o, getter)) {
                return false;
            }
        }
        return true;
    }

    private String getValue(Object o, String getter) {
        Method method = null;
        try {
            method = o.getClass().getMethod(getter);
        } catch (NoSuchMethodException e) {}
        try {
            if (method != null) {
                return (String) method.invoke(o);
            }
        } catch (IllegalAccessException e) {}
        catch (InvocationTargetException e) {}
        return null;
    }

    @Override
    public void initialize(DuplicateField constraintAnnotation) {
        this.getters = constraintAnnotation.getters();
    }
}
