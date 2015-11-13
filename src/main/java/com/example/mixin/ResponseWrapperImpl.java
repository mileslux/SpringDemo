package com.example.mixin;

/**
 * Created by mileslux on 11/13/2015.
 */
public class ResponseWrapperImpl implements ResponseWrapper {

    private final Object originalResponse;
    private final JsonResponse jsonResponse;

    public ResponseWrapperImpl(Object originalResponse, JsonResponse jsonResponse) {
        this.originalResponse = originalResponse;
        this.jsonResponse = jsonResponse;
    }

    @Override
    public boolean hasJsonMixins() {
        return this.jsonResponse.mixins().length > 0;
    }

    @Override
    public JsonResponse getJsonResponse() {
        return this.jsonResponse;
    }

    @Override
    public Object getOriginalResponse() {
        return this.originalResponse;
    }

}
