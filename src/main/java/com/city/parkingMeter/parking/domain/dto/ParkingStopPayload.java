package com.city.parkingMeter.parking.domain.dto;

import com.city.parkingMeter.parking.domain.price.ScalePrice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;


@Getter
public class ParkingStopPayload {

    private final BigDecimal price;

    @JsonCreator
    public ParkingStopPayload(@JsonProperty("price") @Validated BigDecimal price) {
        this.price = ScalePrice.scale(price);
    }
}
