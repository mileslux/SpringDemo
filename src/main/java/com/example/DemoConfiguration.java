package com.example;

import com.example.api.ApiVersionRequestHandlerMapping;
import com.example.db.RateDAO;
import com.example.db.RateDAOImpl;
import com.example.exception.FormattedErrorResponseProvider;
import com.example.exception.FormattedErrorResponseProviderImpl;
import com.example.mixin.JsonResponseMessageConverter;
import com.example.mixin.JsonResponseSupportFactoryBean;
import com.example.service.RateService;
import com.example.service.RateServiceImpl;
import com.example.soap.*;
import com.example.soap.sberbank.SOAPMessageParser;
import com.example.soap.sberbank.SOAPMessageParserImpl;
import com.example.soap.sberbank.SOAPMessageProvider;
import com.example.soap.sberbank.SOAPMessageProviderImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;


/**
 * Created by mileslux on 11/8/2015.
 */
@Configuration
@EnableWebMvc
@Profile("production")
public class DemoConfiguration extends WebMvcConfigurerAdapter {

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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new JsonResponseMessageConverter());
    }

    @Bean
    public InitializingBean initializingBean() {
        return new JsonResponseSupportFactoryBean();
    }

}
