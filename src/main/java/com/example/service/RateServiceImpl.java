package com.example.service;

import com.example.db.RateDAO;
import com.example.domain.Rate;
import com.example.soap.SOAPClient;
import com.example.soap.SOAPMessageParser;
import com.example.soap.SOAPMessageProvider;
import com.example.soap.response.GetCursOnDateXMLData;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import javax.xml.soap.SOAPMessage;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by mileslux on 11/12/2015.
 */
public class RateServiceImpl implements RateService {
    @Autowired
    private RateDAO rateDAO;

    @Autowired
    private SOAPClient soapClient;

    @Autowired
    private SOAPMessageParser soapMessageParser;

    @Autowired
    private SOAPMessageProvider soapMessageProvider;

    private final Object lockObject = new Object();
    private final Multiset<LocalDate> beingRequested = HashMultiset.create();

    public Optional<Rate> get(@NotNull String code, @NotNull LocalDate date) throws Exception {
        Optional<Rate> result;

        synchronized (lockObject) {
            result = rateDAO.get(code, date);
            if (result.isPresent())
                return result;
        }

        SOAPMessage response;

        try {
            response = soapClient.send(
                    soapMessageProvider.createGetCursOnDateXML(date)
            );

            synchronized (lockObject) {
                for (GetCursOnDateXMLData.ValuteCursOnDate valute : soapMessageParser.parseGetCursOnDateXML(response)) {
                    Rate rate = new Rate();
                    rate.setCode(valute.getVchCode());
                    rate.setDate(date);
                    rate.setRate(valute.getVcurs());
                    rateDAO.update(rate);
                }

                result = rateDAO.get(code, date);
            }

        } catch (Exception ex) {
            //log
            throw ex;
        }

        return result;
    }
}
