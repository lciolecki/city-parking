package com.city.parkingMeter.parking.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParkingPriceException extends RuntimeException {

    private ParkingPriceException(String s) {
        super(s);
    }

    public static ParkingPriceException of(String message) {
        return new ParkingPriceException(message);
    }

    public static ParkingPriceException wrongStopPrice(BigDecimal price, BigDecimal excepted) {
        return new ParkingPriceException(String.format(
                "Wrong price to stop %s, it should be %s.",
                price.toString(),
                excepted.toString()
        ));
    }
}
