package com.example.mixin;

import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by mileslux on 11/13/2015.
 */
public class JsonResponseEntityReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public JsonResponseEntityReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
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
        if (jsonResponse != null && returnValue instanceof ResponseEntity) {
            //Wrap the internal ResponseEntity value into ResponseWrapper
            ResponseEntity entity = (ResponseEntity) returnValue;
            returnValue = new ResponseEntity<>(
                    new ResponseWrapperImpl(entity.getBody(), jsonResponse),
                    entity.getHeaders(),
                    entity.getStatusCode()
            );
        }

        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}
