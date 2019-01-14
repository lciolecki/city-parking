package com.city.parkingMeter.parking.domain.dto.response;

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
public class ParkingCheckPaymentResponse {

    private final RegistrationNumber registrationNumber;

    private final Instant finishedAt;

    private final Instant startAt;

    private final BigDecimal price;

    private final HashId id;

    public static ParkingCheckPaymentResponse of(Parking parking, BigDecimal price, Instant finishedAt) {
        return new ParkingCheckPaymentResponse(
                parking.getRegistrationNumber(),
                finishedAt,
                parking.getStartedAt(),
                price,
                parking.getId()
        );
    }
}
