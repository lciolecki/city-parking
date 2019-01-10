package com.city.parkingMeter.parking.domain.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegularPriceCalculator implements PriceCalculator {

    private final double priceFirstHour = 1.0;

    private final double rate = 1.5;
}


