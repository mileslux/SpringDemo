package com.example.mixin;

/**
 * Created by mileslux on 11/13/2015.
 */
interface ResponseWrapper {
    boolean hasJsonMixins();

    JsonResponse getJsonResponse();

    Object getOriginalResponse();
}
