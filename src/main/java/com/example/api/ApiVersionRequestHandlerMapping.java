package com.example.api;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * Created by mileslux on 11/11/2015.
 */
public class ApiVersionRequestHandlerMapping extends RequestMappingHandlerMapping {
    private static final String PREFIX = "v";

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);

        ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        if(methodAnnotation != null) {
            RequestCondition<?> methodCondition = getCustomMethodCondition(method);
            // Concatenate our ApiVersion with the usual request mapping
            info = createApiVersionInfo(methodAnnotation, methodCondition).combine(info);
        } else {
            ApiVersion typeAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
            if(typeAnnotation != null) {
                RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
                // Concatenate our ApiVersion with the usual request mapping
                info = createApiVersionInfo(typeAnnotation, typeCondition).combine(info);
            }
        }

        return info;
    }

    private RequestMappingInfo createApiVersionInfo(ApiVersion annotation, RequestCondition<?> customCondition) {
        String[] versions = annotation.versions();
        boolean latest = annotation.latest();
        int length = versions.length + (latest ? 2 : 0);
        String[] patterns = new String[length];

        for (int i = 0; i < versions.length; i++) {
            patterns[i] = PREFIX + versions[i];
        }

        if (latest) {
            patterns[versions.length] = "";
            patterns[versions.length + 1] = "latest";
        }

        return new RequestMappingInfo(
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(),
                new ParamsRequestCondition(),
                new HeadersRequestCondition(),
                new ConsumesRequestCondition(),
                new ProducesRequestCondition(),
                customCondition);
    }

    @Override
    protected boolean isHandler(Class<?> beanType) {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(beanType);
        for (Method method : methods) {
            if (AnnotationUtils.findAnnotation(method, ApiVersion.class) != null)
                return true;
        }
        return false;
    }
}
