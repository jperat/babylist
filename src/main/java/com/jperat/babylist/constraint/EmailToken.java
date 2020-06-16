package com.jperat.babylist.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=EmailTokenValidator.class)
public @interface EmailToken {

    String message() default "com.jperat.babylist.constraints.EmailToken.message";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
