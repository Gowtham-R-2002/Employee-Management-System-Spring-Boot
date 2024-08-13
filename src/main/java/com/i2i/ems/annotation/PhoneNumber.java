package com.i2i.ems.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * <p>
 * Custom validation annotation for phone number
 * </p>
 */
@Target( { FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumber {
    public String message() default "Phone number must be 10 digits long!";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
