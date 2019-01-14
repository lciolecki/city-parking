package com.city.parkingMeter.parking.domain.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class DisabledPriceCalculator implements PriceCalculator {

    private final BigDecimal priceFirstHour = new BigDecimal(0.0);

    private final double rate = 1.2;
}
