package com.example.controllers;

import com.example.exceptions.FormattedError;
import com.example.exceptions.FormattedErrorResponse;
import com.example.exceptions.FormattedErrorResponseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by mileslux on 11/12/2015.
 */
@ControllerAdvice
public class ExceptionController {
    @Autowired
    private FormattedErrorResponseProvider formattedErrorResponseProvider;

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FormattedErrorResponse exception(Exception exception) {
        return formattedErrorResponseProvider.getFormattedError(exception);
    }
}
