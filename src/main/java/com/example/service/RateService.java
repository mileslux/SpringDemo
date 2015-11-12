package com.example.service;

import com.example.domain.Rate;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by mileslux on 11/12/2015.
 */
public interface RateService {
    Optional<Rate> get(@NotNull String code, @NotNull LocalDate date) throws Exception;
}
