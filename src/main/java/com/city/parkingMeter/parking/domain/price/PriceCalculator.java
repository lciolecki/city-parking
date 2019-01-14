package com.city.parkingMeter.parking.domain.price;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public interface PriceCalculator {

    BigDecimal getPriceFirstHour();

    double getRate();

    default BigDecimal calculate(Instant startedAt, Instant finishedAt) {
        long hours = calculateHoursNumber(startedAt, finishedAt);
        BigDecimal price = amount(1, new BigDecimal(0.0), hours);

        return ScalePrice.scale(price);
    }

    default long calculateHoursNumber(Instant startedAt, Instant finishedAt) {
        if (Objects.isNull(startedAt) || Objects.isNull(finishedAt)) {
            throw PriceCounterException.of("Params startedAt and finishedAt should be not null.");
        }

        if (startedAt.isAfter(finishedAt)) {
            throw PriceCounterException.of("Param startedAt should be before than finishedAt.");
        }

        return Duration.between(startedAt,finishedAt).toHours() + 1;
    }

    default BigDecimal amount(long actualHour, BigDecimal previousPrice, long numberOfHours) {
        BigDecimal actual = new BigDecimal(2.0);

        if (actualHour == 1) {
            if (numberOfHours == 1) {
                return getPriceFirstHour();
            }

            return getPriceFirstHour().add(amount(++actualHour, getPriceFirstHour(), numberOfHours));
        }

        if (actualHour == 2 && numberOfHours == 2) {
            return actual;
        } else if (actualHour > 2) {
            actual = previousPrice.multiply(new BigDecimal(getRate()));
        }

        if (actualHour >= numberOfHours) {
            return actual;
        }

        return actual.add(amount(++actualHour, actual, numberOfHours));
    }
}
