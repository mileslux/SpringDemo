package com.example.constraint;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mileslux on 11/10/2015.
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=DateConstraintValidator.class)
public @interface DateConstraint {
    String message() default "{com.example.constraint.DateConstraint.message}";
    Class[] groups() default {};
    Class[] payload() default {};
}
