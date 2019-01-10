package com.city.parkingMeter.parking.service;


import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.ParkingStatus;
import com.city.parkingMeter.parking.domain.price.ScalePrice;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import com.city.parkingMeter.parking.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final ParkingRepository parkingRepository;

    public boolean isVehicleStartedParking(RegistrationNumber registrationNumber) {
        Optional<Parking> existParking = parkingRepository.fetchStartedByRegistrationNumber(registrationNumber);
        return existParking.isPresent();
    }

    public BigDecimal summary(Instant from, Instant to) {
        double summary = parkingRepository.fetchFinishedSummary(from, to);
        return ScalePrice.scale(new BigDecimal(summary));
    }
}
