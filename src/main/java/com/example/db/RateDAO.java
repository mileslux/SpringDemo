package com.example.db;

import com.example.domain.Rate;

import java.time.LocalDate;
import java.util.Optional;
import javax.validation.constraints.NotNull;

/**
 * Created by mileslux on 11/8/2015.
 */
public interface RateDAO {
    void update(@NotNull Rate rate);
    Optional<Rate> get(@NotNull String code, @NotNull LocalDate date);
    Boolean containsDate(@NotNull LocalDate date);
}
