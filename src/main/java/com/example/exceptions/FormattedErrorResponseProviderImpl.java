package com.example.exceptions;

import javax.validation.ConstraintViolationException;
import java.util.stream.Stream;

/**
 * Created by mileslux on 11/12/2015.
 */
public class FormattedErrorResponseProviderImpl implements FormattedErrorResponseProvider {
    @Override
    public FormattedErrorResponse getFormattedError(ConstraintViolationException exception) {
        Stream<FormattedError> stream = exception.getConstraintViolations().stream()
                .map(violation -> new FormattedError(violation.getMessage()));
        FormattedError[] errors = stream.toArray(size -> new FormattedError[size]);

        return new FormattedErrorResponse("Validation failed", errors);
    }

    @Override
    public FormattedErrorResponse getFormattedError(Exception exception) {
        return new FormattedErrorResponse("General error",
                new FormattedError[] { new FormattedError(exception.getMessage())});
    }
}
