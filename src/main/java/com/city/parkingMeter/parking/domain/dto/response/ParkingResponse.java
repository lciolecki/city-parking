package com.city.parkingMeter.parking.domain.dto.response;

import com.city.parkingMeter.parking.domain.DriverType;
import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.vo.HashId;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingResponse {

    private final RegistrationNumber registrationNumber;

    private final DriverType driverType;

    private final BigDecimal price;

    private final Instant finishedAt;

    private final Instant startAt;

    private final HashId id;

    public static ParkingResponse fromParkingEntity(Parking parking) {
        return new ParkingResponse(
                parking.getRegistrationNumber(),
                parking.getDriverType(),
                parking.getPrice(),
                parking.getFinishedAt(),
                parking.getStartedAt(),
                parking.getId()
        );
    }

}
