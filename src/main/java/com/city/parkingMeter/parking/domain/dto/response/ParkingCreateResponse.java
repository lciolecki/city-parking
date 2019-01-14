package com.city.parkingMeter.parking.domain.dto.response;

import com.city.parkingMeter.parking.domain.DriverType;
import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.vo.HashId;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingCreateResponse {

    private final RegistrationNumber registrationNumber;

    private final DriverType driverType;

    private final HashId id;

    public static ParkingCreateResponse of(Parking parking) {
        return new ParkingCreateResponse(
                parking.getRegistrationNumber(),
                parking.getDriverType(),
                parking.getId()
        );
    }

}
