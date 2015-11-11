package com.example.exceptions;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

/**
 * Created by mileslux on 11/11/2015.
 */
public class ExceptionResponse {
    private String path;
    private Set<String> errors;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<String> getErrors() {
        return errors;
    }

    public void setErrors(Set<String> errors) {
        this.errors = errors;
    }
}
