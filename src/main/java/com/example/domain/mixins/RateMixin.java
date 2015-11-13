package com.example.domain.mixins;

import com.example.utils.RateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

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
