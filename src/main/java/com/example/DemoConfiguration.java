package com.example;

import com.example.db.RateDAO;
import com.example.db.RateTable;
import com.example.soap.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Created by mileslux on 11/8/2015.
 */
@Configuration
public class DemoConfiguration {

    @Bean
    public RateDAO rateDAO() {
        return new RateTable();
    }

    @Bean
    public SOAPMessageParser soapMessageParser() {
        return new MySOAPMessageParser();
    }

    @Bean
    public SOAPMessageProvider soapMessageProvider() {
        return new MySOAPMessageProvider();
    }

    @Bean
    public SOAPClient soapClient() {
        return new MySOAPClient("http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx", 10000, 10000);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
