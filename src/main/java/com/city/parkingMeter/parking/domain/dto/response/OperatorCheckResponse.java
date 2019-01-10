package com.city.parkingMeter.parking.domain.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OperatorCheckResponse {
    private final boolean started;
}
