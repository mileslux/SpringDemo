package com.example.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by mileslux on 11/12/2015.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormattedError {
    private String description;

    public FormattedError() {}

    public FormattedError(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
