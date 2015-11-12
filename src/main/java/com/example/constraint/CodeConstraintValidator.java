package com.example.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Created by mileslux on 11/10/2015.
 */
public class CodeConstraintValidator implements ConstraintValidator<CodeConstraint, String> {
    private static final String CODE_PATTERN = "^[A-Z]{3}$";
    private static final Pattern PATTERN = Pattern.compile(CODE_PATTERN);

    @Override
    public void initialize(CodeConstraint constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!PATTERN.matcher(value).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid currency code format")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
