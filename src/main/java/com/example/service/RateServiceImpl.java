package com.example.service;

import com.example.db.RateDAO;
import com.example.domain.Rate;
import com.example.soap.SOAPClient;
import com.example.soap.sberbank.SOAPMessageParser;
import com.example.soap.sberbank.SOAPMessageProvider;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import javax.xml.soap.SOAPMessage;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private final LoadingCache<LocalDate, Map<String, Rate>> loadingCache = CacheBuilder.newBuilder()
            .build(
            new CacheLoader<LocalDate, Map<String, Rate>>() {
                public Map<String, Rate> load(LocalDate date) throws Exception {
                    SOAPMessage response = soapClient.send(
                            soapMessageProvider.createGetCursOnDateXML(date)
                    );

                    response.writeTo(System.out);

                    return ImmutableMap.<String, Rate>builder()
                            .putAll(
                                    soapMessageParser.parseGetCursOnDateXMLResponse(response)
                                            .getGetCursOnDateXMLResult()
                                            .getValuteData()
                                            .getValuteCursOnDate()
                                            .stream()
                                            .map(v -> {
                                                Rate rate = new Rate();
                                                rate.setCode(v.getVchCode());
                                                rate.setDate(date);
                                                rate.setRate(v.getVcurs());
                                                return rate;
                                            }).collect(Collectors.toMap(Rate::getCode, Function.identity()))
                            ).build();
                }
            });

    public Optional<Rate> get(@NotNull String code, @NotNull LocalDate date) throws Exception {
        Map<String, Rate> rates = loadingCache.get(date);
        return Optional.ofNullable(rates.get(code));
    }
}
