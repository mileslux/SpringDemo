package com.example.controller;

import com.example.api.ApiVersion;
import com.example.domain.mixins.RateMixin;
import com.example.mixin.JsonMixin;
import com.example.mixin.JsonResponse;
import com.example.service.RateService;
import com.example.domain.Rate;
import com.example.exception.FormattedErrorResponse;
import com.example.exception.FormattedErrorResponseProvider;
import com.example.constraint.CodeConstraint;
import com.example.constraint.DateConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by mileslux on 11/8/2015.
 */
@RestController
@Validated
@RequestMapping("/rate")
public class RateController {
    @Autowired
    private FormattedErrorResponseProvider formattedErrorResponseProvider;

    @Autowired
    private RateService rateService;

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    @ApiVersion(versions = {"1"}, latest = true)
    @JsonResponse(mixins = {
            @JsonMixin(target = Rate.class, mixin = RateMixin.class)
    })
    public ResponseEntity<?> getRate(@CodeConstraint @PathVariable("code") String code) {
        return getRate(code, LocalDate.now());
    }

    @RequestMapping(value = "/{code}/{date}", method = RequestMethod.GET)
    @ApiVersion(versions = {"1"}, latest = true)
    @JsonResponse(mixins = {
            @JsonMixin(target = Rate.class, mixin = RateMixin.class)
    })
    public ResponseEntity<?> getRate(@CodeConstraint @PathVariable("code") String code,
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date) {
        Optional<Rate> result;

        try {
            result = rateService.get(code, date);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        return result.isPresent() ? new ResponseEntity<>(result.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public FormattedErrorResponse handleError(ConstraintViolationException exception) {
        return formattedErrorResponseProvider.getFormattedError(exception);
    }
}

