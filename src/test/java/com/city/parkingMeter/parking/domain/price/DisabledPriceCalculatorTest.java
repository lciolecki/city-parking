package com.city.parkingMeter.parking.domain.price;

import com.city.parkingMeter.parking.domain.DriverType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;


public class DisabledPriceCalculatorTest {

    PriceCalculator calculator;

    public DisabledPriceCalculatorTest() {
        this.calculator = PriceCounterFactory.create(DriverType.DISABLED);
    }

    @Test(expected = PriceCounterException.class)
    public void testStartedAtAfterFinishedAt() {
        BigDecimal price = calculator.calculate(Instant.parse("2019-01-09T21:38:58.051Z"), Instant.parse("2019-01-09T17:38:57.051Z"));
    }

    @Test(expected = PriceCounterException.class)
    public void testNullDates() {
        BigDecimal price = calculator.calculate(null, null);
    }

    @Test
    public void testOneHourParkingShouldBeFree() {
        BigDecimal price = calculator.calculate(Instant.now(), Instant.now());
        BigDecimal excepted = ScalePrice.scale(new BigDecimal(0));

        Assert.assertEquals(excepted, price);
    }

    @Test
    public void testTwoHourParking() {
        BigDecimal price = calculator.calculate(Instant.parse("2019-01-09T17:38:57.051Z"), Instant.parse("2019-01-09T18:38:58.051Z"));
        BigDecimal excepted = ScalePrice.scale(new BigDecimal(2));

        Assert.assertEquals(excepted, price);
    }

    @Test
    public void testFiveHourParking() {
        BigDecimal price = calculator.calculate(Instant.parse("2019-01-09T17:38:57.051Z"), Instant.parse("2019-01-09T21:38:58.051Z"));
        BigDecimal excepted = ScalePrice.scale(new BigDecimal(10.74));

        Assert.assertEquals(excepted, price);
    }
}
