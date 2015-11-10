package com.example.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

/**
 * Created by mileslux on 11/10/2015.
 */
public class DateConstraintValidator implements ConstraintValidator<DateConstraint, String> {
    @Override
    public void initialize(DateConstraint constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException ex) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid date format")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
