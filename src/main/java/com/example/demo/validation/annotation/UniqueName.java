package com.example.demo.validation.annotation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.example.demo.validation.logic.UniqueNameValidator;

@Constraint(validatedBy = UniqueNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueName {
    String message() default "Invalid name, object already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> type() default Object.class;
}
