package com.city.parkingMeter.parking.domain.applicable;

import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.ParkingStatus;
import com.city.parkingMeter.parking.domain.exception.ParkingStatusException;

import java.util.Objects;

public final class ApplicableUtil {
    public static <T extends Parking> T applyWith(T object, ParkingStatus status) {
        if (!Objects.equals(object.getParkingStatus(), status)) {
            throw ParkingStatusException.invalidStatus(object.getParkingStatus(), status);
        }

        return object;
    }
}