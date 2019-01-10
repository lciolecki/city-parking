package com.city.parkingMeter.parking.domain.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class OperatorSummaryResponse {

    private final BigDecimal summary;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final Instant from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final Instant to;
}
