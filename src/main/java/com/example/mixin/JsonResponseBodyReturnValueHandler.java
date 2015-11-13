package com.example.mixin;

import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by mileslux on 11/13/2015.
 */
public final class JsonResponseBodyReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public JsonResponseBodyReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {

        JsonResponse jsonResponse = returnType.getMethodAnnotation(JsonResponse.class);
        if (jsonResponse != null) {
            //Wrap the returnValue into ResponseWrapper
            returnValue = new ResponseWrapperImpl(returnValue, jsonResponse);
        }

        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}
