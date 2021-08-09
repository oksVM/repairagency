package com.example.repairagency.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    //TODO locale
    String message() default "invalid.email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}