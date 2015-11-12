package com.example;

import com.example.api.ApiVersionRequestHandlerMapping;
import com.example.db.RateDAO;
import com.example.db.RateDAOImpl;
import com.example.exception.FormattedErrorResponseProvider;
import com.example.exception.FormattedErrorResponseProviderImpl;
import com.example.service.RateService;
import com.example.service.RateServiceImpl;
import com.example.soap.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Created by mileslux on 11/8/2015.
 */
@Configuration
@EnableWebMvc
@Profile("production")
public class DemoConfiguration {

    @Bean
    public RateDAO rateDAO() {
        return new RateDAOImpl();
    }

    @Bean
    public RateService rateService() {
        return new RateServiceImpl();
    }

    @Bean
    public SOAPMessageParser soapMessageParser() {
        return new SOAPMessageParserImpl();
    }

    @Bean
    public SOAPMessageProvider soapMessageProvider() {
        return new SOAPMessageProviderImpl();
    }

    @Bean
    public SOAPClient soapClient() {
        return new SOAPClientImpl();
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
