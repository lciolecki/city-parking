package com.city.parkingMeter.parking.domain.price;

public class PriceCounterException extends IllegalArgumentException {

    private PriceCounterException(String s) {
        super(s);
    }

    public static PriceCounterException of(String message) {
        return new PriceCounterException(message);
    }
}
