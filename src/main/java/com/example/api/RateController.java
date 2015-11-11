package com.example.api;

import com.example.core.Rate;
import com.example.db.RateDAO;
import com.example.soap.SOAPClient;
import com.example.soap.SOAPMessageParser;
import com.example.soap.SOAPMessageProvider;
import com.example.soap.response.GetCursOnDateXMLData;
import com.example.constraints.CodeConstraint;
import com.example.constraints.DateConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.soap.SOAPMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by mileslux on 11/8/2015.
 */
@RestController
@Validated
@RequestMapping("/api/rate")
public class RateController {

    @Autowired
    private RateDAO rateDAO;

    @Autowired
    private SOAPClient soapClient;

    @Autowired
    private SOAPMessageParser soapMessageParser;

    @Autowired
    private SOAPMessageProvider soapMessageProvider;

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ResponseEntity<?> getRate(@CodeConstraint @PathVariable("code") String code) {
        return getRate(code, LocalDate.now().format(DateTimeFormatter.ISO_DATE));
    }

    @RequestMapping(value = "/{code}/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getRate(@CodeConstraint @PathVariable("code") String code,
                                     @DateConstraint @PathVariable("date") String date) {

        Optional<Rate> optional = rateDAO.get(code, date);

        if (optional.isPresent())
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);

        if (rateDAO.containsDate(date))
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);

        try {
            SOAPMessage response = soapClient.send(
                    soapMessageProvider.createGetCursOnDateXML(date)
            );

            for (GetCursOnDateXMLData.ValuteCursOnDate valute : soapMessageParser.parseGetCursOnDateXML(response)) {
                Rate rate = new Rate();
                rate.setCode(valute.getVchCode());
                rate.setDate(date);
                rate.setRate(valute.getVcurs());
                rateDAO.update(rate);
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        optional = rateDAO.get(code, date);

        if (optional.isPresent())
            return new ResponseEntity<Rate>(optional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException exception) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        for (ConstraintViolation violation : exception.getConstraintViolations()) {
            mav.addObject("message", violation.getMessage());
        }
        mav.addObject("url", req.getRequestURL());
        return mav;
    }
}

