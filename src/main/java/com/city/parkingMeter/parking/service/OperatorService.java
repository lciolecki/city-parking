package com.city.parkingMeter.parking.service;


import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.price.ScalePrice;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import com.city.parkingMeter.parking.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final ParkingRepository parkingRepository;

    public boolean isVehicleStartedParking(final RegistrationNumber registrationNumber) {
        final Optional<Parking> existParking = parkingRepository.fetchStartedByRegistrationNumber(registrationNumber);
        return existParking.isPresent();
    }

    public BigDecimal summary(final Instant from, final Instant to) {
        final double summary = parkingRepository.fetchFinishedSummary(from, to);
        return ScalePrice.scale(new BigDecimal(summary));
    }
}
