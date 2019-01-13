package com.city.parkingMeter.parking.domain.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class RegularPriceCalculator implements PriceCalculator {

    private final BigDecimal priceFirstHour = new BigDecimal(1.0);

    private final double rate = 1.5;
}


