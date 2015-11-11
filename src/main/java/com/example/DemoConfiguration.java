package com.example;

import com.example.api.ApiVersionRequestHandlerMapping;
import com.example.db.RateDAO;
import com.example.db.RateTable;
import com.example.exceptions.FormattedErrorResponseProvider;
import com.example.exceptions.FormattedErrorResponseProviderImpl;
import com.example.soap.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Created by mileslux on 11/8/2015.
 */
@Configuration
@EnableWebMvc
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
        return new MySOAPClient();
    }

    @Bean
    public FormattedErrorResponseProvider formattedErrorResponseProvider() {
        return new FormattedErrorResponseProviderImpl();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new ApiVersionRequestHandlerMapping();
    }
}
