package com.example.db;

import com.example.core.Rate;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Created by mileslux on 11/8/2015.
 */
public class RateTable implements RateDAO {

    private final Table<String, String, Rate> table = HashBasedTable.create();

    @Override
    public void update(@NotNull Rate rate) {
        table.put(rate.getCode(), rate.getDate(), rate);
    }

    @Override
    public Optional<Rate> get(@NotNull String code, @NotNull String date) {
        return Optional.ofNullable(table.get(code, date));
    }

    @Override
    public Boolean containsDate(@NotNull String date) {
        return table.containsRow(date);
    }
}
