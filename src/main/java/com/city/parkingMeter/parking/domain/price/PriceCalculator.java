package com.city.parkingMeter.parking.domain.price;

import org.joda.time.Hours;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public interface PriceCalculator {

    double getPriceFirstHour();

    double getRate();

    default BigDecimal calculate(Instant startedAt, Instant finishedAt) {
        int hours = hours(startedAt, finishedAt);
        double sum = amount(1, hours, 0.0);
        BigDecimal price = new BigDecimal(sum);

        return ScalePrice.scale(price);
    }

    default int hours(Instant startedAt, Instant finishedAt) {

        if (Objects.isNull(startedAt) || Objects.isNull(finishedAt)) {
            throw PriceCounterException.of("Params startedAt and finishedAt should be not null.");
        }

        if (startedAt.isAfter(finishedAt)) {
            throw PriceCounterException.of("Param startedAt should be before than finishedAt.");
        }

        return Hours.hoursBetween(
            org.joda.time.Instant.ofEpochMilli(startedAt.toEpochMilli()),
            org.joda.time.Instant.ofEpochMilli(finishedAt.toEpochMilli())
        ).getHours() + 1;
    }

    default double amount(int hour, int hours, double previous)  {
        double actual = 2.0;

        if (hour == 1) {
            if (hours == 1) {
                return getPriceFirstHour();
            }

            return getPriceFirstHour() + amount(++hour, hours, getPriceFirstHour());
        }

        if (hour == 2 && hours == 2) {
            return actual;
        } else if (hour > 2) {
            actual = (previous * getRate());
        }

        if (hour >= hours) {
            return actual;
        }

        return actual + amount(++hour, hours, actual);
    }
}
