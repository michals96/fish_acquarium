package com.example.demo.validation.annotation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.example.demo.validation.logic.LimitCapacityValidator;

@Constraint(validatedBy = LimitCapacityValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitCapacity {
    String message() default "Capacity limit exceeded";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
