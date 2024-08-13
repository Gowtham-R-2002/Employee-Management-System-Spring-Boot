package com.i2i.ems.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * <p>
 * Validator class for phone number
 *</p>
 *
 * @author Gowtham r
 * @version  1.4
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Long> {

    /**
     * <p>
     * Validates the given phone number's length
     * </p>
     * @param phoneNumber  The phone number to be validated
     * @param constraintValidatorContext The context of the constrainValidation
     * @return true if valid else false
     */
    @Override
    public boolean isValid(Long phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return 10 == phoneNumber.toString().length();
    }
}
