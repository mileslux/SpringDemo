package com.example.exceptions;

import javax.validation.ConstraintViolationException;

/**
 * Created by mileslux on 11/12/2015.
 */
public interface FormattedErrorResponseProvider {
    FormattedErrorResponse getFormattedError(ConstraintViolationException exception);
    FormattedErrorResponse getFormattedError(Exception exception);
}
