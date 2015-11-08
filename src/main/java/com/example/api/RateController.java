package com.example.api;

import com.example.core.Rate;
import com.example.db.RateDAO;
import com.example.soap.SOAPClient;
import com.example.soap.SOAPMessageParser;
import com.example.soap.SOAPMessageProvider;
import com.example.soap.response.GetCursOnDateXMLData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.soap.SOAPMessage;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by mileslux on 11/8/2015.
 */
@RestController
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

    @RequestMapping(value = "/{code}/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrency(@PathVariable("code") String code,
                            @PathVariable("date") Optional<String> date) {

        String fixedDate = date.isPresent() ? date.get() : LocalDate.now().toString();
        Optional<Rate> optional = rateDAO.get(code, fixedDate);

        if (optional.isPresent())
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);

        if (rateDAO.containsDate(fixedDate))
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);

        try {
            SOAPMessage response = soapClient.send(
                    soapMessageProvider.createGetCursOnDateXML(fixedDate)
            );

            for (GetCursOnDateXMLData.ValuteCursOnDate valute : soapMessageParser.parseGetCursOnDateXML(response)) {
                Rate rate = new Rate();
                rate.setCode(valute.getVchCode());
                rate.setDate(fixedDate);
                rate.setRate(valute.getVcurs());
                rateDAO.update(rate);
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        optional = rateDAO.get(code, fixedDate);

        if (optional.isPresent())
            return new ResponseEntity<Rate>(optional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }
}

