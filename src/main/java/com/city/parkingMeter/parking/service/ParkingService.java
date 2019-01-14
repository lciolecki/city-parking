package com.city.parkingMeter.parking.service;


import com.city.parkingMeter.infrastructure.exception.RecordNotFoundException;
import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.ParkingStatus;
import com.city.parkingMeter.parking.domain.applicable.ApplicableUtil;
import com.city.parkingMeter.parking.domain.dto.ParkingPayload;
import com.city.parkingMeter.parking.domain.dto.ParkingStopPayload;
import com.city.parkingMeter.parking.domain.exception.ParkingPriceException;
import com.city.parkingMeter.parking.domain.exception.ParkingStatusException;
import com.city.parkingMeter.parking.domain.price.PriceCalculator;
import com.city.parkingMeter.parking.domain.price.PriceCounterFactory;
import com.city.parkingMeter.parking.domain.vo.HashId;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import com.city.parkingMeter.parking.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public Parking fetchById(final HashId hashId) {
        return parkingRepository.findById(hashId)
                .orElseThrow(() -> RecordNotFoundException.notFound(Parking.class, hashId));
    }

    public final Parking fetch(final HashId id, final ParkingStatus parkingStatus) {
        return ApplicableUtil.applyWith(fetchById(id), parkingStatus);
    }

    public Parking create(final ParkingPayload payload) {
        if (hasVehicleStartedParking(payload.getRegistrationNumber())) {
            throw ParkingStatusException.startedException(payload.getRegistrationNumber());
        }

        final Parking parking = Parking.createFromPayload(payload);

        return parkingRepository.save(parking);
    }

    public Parking stop(final HashId id, final ParkingStopPayload payload) {
        Parking parking = fetch(id, ParkingStatus.STARTED);

        final Instant finishedAt = Instant.now();
        final BigDecimal price = calculatePayment(parking, finishedAt);
        if (!Objects.equals(payload.getPrice(), price)) {
            throw ParkingPriceException.wrongStopPrice(payload.getPrice(), price);
        }

        parking.finish(payload, finishedAt);

        return parkingRepository.save(parking);
    }

    public BigDecimal calculatePayment(final Parking parking, final Instant finishedAt) {
        final PriceCalculator priceCalculator = PriceCounterFactory.create(parking.getDriverType());
        final BigDecimal price = priceCalculator.calculate(parking.getStartedAt(), finishedAt);

        return price;
    }

    boolean hasVehicleStartedParking(final RegistrationNumber registrationNumber) {
        final Optional<Parking> parkingOptional = parkingRepository.fetchStartedByRegistrationNumber(registrationNumber);
        return parkingOptional.isPresent();
    }
}
