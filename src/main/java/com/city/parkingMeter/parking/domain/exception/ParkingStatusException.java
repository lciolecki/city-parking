package com.city.parkingMeter.parking.domain.exception;

import com.city.parkingMeter.parking.domain.ParkingStatus;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParkingStatusException extends RuntimeException {

    private ParkingStatusException(String s) {
        super(s);
    }

    public static ParkingStatusException of(String message) {
        return new ParkingStatusException(message);
    }

    public static ParkingStatusException startedException(RegistrationNumber registrationNumber) {
        return new ParkingStatusException(String.format(
                "Vehicle with registration number %s has already started parking.",
                registrationNumber.toString()
        ));
    }

    public static ParkingStatusException invalidStatus(ParkingStatus status, ParkingStatus expected) {
        return new ParkingStatusException(String.format(
                "Invalid parking status: %s. Expected campaign status: %s.", status, expected
        ));
    }
}
