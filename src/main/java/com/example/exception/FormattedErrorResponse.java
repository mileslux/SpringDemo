package com.example.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by mileslux on 11/11/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormattedErrorResponse {
    private String message;
    private FormattedError[] errors;

    public FormattedErrorResponse() {}

    public FormattedErrorResponse(String message, FormattedError[] errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FormattedError[] getErrors() {
        return errors;
    }

    public void setErrors(FormattedError[] errors) {
        this.errors = errors;
    }
}
