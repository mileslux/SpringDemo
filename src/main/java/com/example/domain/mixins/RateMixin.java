package com.example.domain.mixins;

import com.example.domain.serializers.RateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by mileslux on 11/13/2015.
 */
@JsonSerialize(using = RateSerializer.class)
public interface RateMixin {
    String getCode();

    BigDecimal getRate();

    LocalDate getDate();
}
