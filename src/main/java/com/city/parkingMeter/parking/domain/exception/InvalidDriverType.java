package com.city.parkingMeter.parking.domain.exception;

import com.city.parkingMeter.parking.domain.DriverType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDriverType extends RuntimeException {

    private InvalidDriverType(String s) {
        super(s);
    }

    public static InvalidDriverType of(String message) {
        return new InvalidDriverType(message);
    }

    public static InvalidDriverType of(DriverType campaignType) {
        return new InvalidDriverType(String.format("Invalid driver type: %s. Expected types: %s.", campaignType.toString()));
    }
}
