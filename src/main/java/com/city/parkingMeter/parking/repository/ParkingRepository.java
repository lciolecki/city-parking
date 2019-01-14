package com.city.parkingMeter.parking.repository;

import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.ParkingStatus;
import com.city.parkingMeter.parking.domain.vo.HashId;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;


public interface ParkingRepository extends JpaRepository<Parking, HashId> {

    Optional<Parking> findByRegistrationNumberAndParkingStatus(final RegistrationNumber registrationNumber, final ParkingStatus parkingStatus);

    default Optional<Parking> fetchStartedByRegistrationNumber(final RegistrationNumber registrationNumber) {
        return findByRegistrationNumberAndParkingStatus(registrationNumber, ParkingStatus.STARTED);
    }

    @Query(value = "SELECT COALESCE(SUM(p.price), 0.00) as summary FROM Parking p WHERE p.parkingStatus = :status AND (p.startedAt between :from AND :to)")
    BigDecimal fetchSummary(final @Param("from") Instant from, final  @Param("to") Instant to, @Param("status") ParkingStatus status);

    default BigDecimal fetchFinishedSummary(final Instant from, final Instant to) {
        return fetchSummary(from, to, ParkingStatus.FINISHED);
    }
}
