package com.jperat.babylist.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=DuplicateFieldValidator.class)
public @interface DuplicateField {

    String message() default "com.jperat.babylist.constraints.DuplicateField.message";
    Class<?>[] groups() default { };
    String[] getters();
    Class<? extends Payload>[] payload() default { };
}
