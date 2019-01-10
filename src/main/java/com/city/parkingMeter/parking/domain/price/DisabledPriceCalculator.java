package com.city.parkingMeter.parking.domain.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DisabledPriceCalculator implements PriceCalculator {

    private final double priceFirstHour = 0.0;

    private final double rate = 1.2;
}
