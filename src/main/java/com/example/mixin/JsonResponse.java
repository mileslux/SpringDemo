package com.example.mixin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mileslux on 11/13/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonResponse {
    JsonMixin[] mixins();
}
