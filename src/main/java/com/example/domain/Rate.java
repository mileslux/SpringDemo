package com.example.domain;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by mileslux on 11/6/2015.
 */
public class Rate {
    @NotNull
    private String code;
    @NotNull
    private BigDecimal rate;
    @NotNull
    private LocalDate date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate = (Rate) o;

        if (!code.equals(rate.code)) return false;
        if (!this.rate.equals(rate.rate)) return false;
        return date.equals(rate.date);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + rate.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
