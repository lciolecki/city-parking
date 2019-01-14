package com.city.parkingMeter.parking.domain.dto;

import com.city.parkingMeter.parking.domain.DriverType;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
public class ParkingPayload {

    @NotNull
    private final RegistrationNumber registrationNumber;

    @NotNull
    private final DriverType driverType;

    @JsonCreator
    public ParkingPayload(
            @JsonProperty("registrationNumber") @Validated RegistrationNumber registrationNumber,
            @JsonProperty("driverType") DriverType driverType
    ) {
        this.registrationNumber = registrationNumber;
        this.driverType = driverType;
    }
}
