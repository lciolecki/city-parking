package com.city.parkingMeter.parking.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class OperatorSummaryResponse {

    @JsonFormat
    private  final BigDecimal summary;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final Instant from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final Instant to;
}
