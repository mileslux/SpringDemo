package com.example.db;

import com.example.domain.Rate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by mileslux on 11/8/2015.
 */
public class RateDAOImpl implements RateDAO {

    private final Table<String, LocalDate, Rate> table = HashBasedTable.create();

    @Override
    public void update(@NotNull Rate rate) {
        table.put(rate.getCode(), rate.getDate(), rate);
    }

    @Override
    public Optional<Rate> get(@NotNull String code, @NotNull LocalDate date) {
        return Optional.ofNullable(table.get(code, date));
    }

    @Override
    public Boolean containsDate(@NotNull LocalDate date) {
        return table.containsRow(date);
    }
}
