package com.city.parkingMeter.parking.controller;

import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.dto.response.OperatorSummaryResponse;
import com.city.parkingMeter.parking.domain.dto.response.ParkingResponse;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import com.city.parkingMeter.parking.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operator")
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping("/vehicle/{registrationNumber}")
    public ParkingResponse check(@PathVariable @Valid RegistrationNumber registrationNumber) {
        Parking parking = operatorService.fetchStartedByRegistrationNumber(registrationNumber);
        return ParkingResponse.of(parking);
    }

    @GetMapping("/summary")
    public OperatorSummaryResponse summary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        Instant fromInstant = from.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant toInstant = to.atStartOfDay(ZoneId.systemDefault()).toInstant();
        BigDecimal summary = operatorService.summary(fromInstant, toInstant);

        return new OperatorSummaryResponse(summary, fromInstant, toInstant);
    }
}
