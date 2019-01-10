package com.city.parkingMeter.parking.controller;

import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.ParkingStatus;
import com.city.parkingMeter.parking.domain.dto.ParkingPayload;
import com.city.parkingMeter.parking.domain.dto.ParkingStopPayload;
import com.city.parkingMeter.parking.domain.dto.response.ParkingCheckPaymentResponse;
import com.city.parkingMeter.parking.domain.dto.response.ParkingCreateResponse;
import com.city.parkingMeter.parking.domain.dto.response.ParkingResponse;
import com.city.parkingMeter.parking.domain.vo.HashId;
import com.city.parkingMeter.parking.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parking")
public class ParkingMeterController {

    private final ParkingService parkingService;

    @PostMapping("/")
    public ParkingCreateResponse create(@RequestBody @Validated ParkingPayload payload) {
        Parking parking = parkingService.create(payload);
        return ParkingCreateResponse.fromParkingEntity(parking);
    }

    @GetMapping("/{id}")
    public ParkingResponse retrieve(@PathVariable @Valid HashId id) {
        Parking parking = parkingService.fetchById(id);
        return ParkingResponse.fromParkingEntity(parking);
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<Void> stop(@PathVariable @Valid HashId id, @RequestBody @Validated ParkingStopPayload payload) {
        Parking parking = parkingService.stop(id, payload);
        return ResponseEntity.noContent().location(fromCurrentRequest().path("/{id}").buildAndExpand(parking.getIdAsString()).toUri()).build();
    }

    @GetMapping("/{id}/check-payment")
    public ParkingCheckPaymentResponse check(@PathVariable @Valid HashId id) {
        Instant finishedAt = Instant.now();
        Parking parking = parkingService.fetch(id, ParkingStatus.STARTED);
        BigDecimal price = parkingService.calculatePayment(parking, finishedAt);

        return ParkingCheckPaymentResponse.of(parking, price, finishedAt);
    }
}
