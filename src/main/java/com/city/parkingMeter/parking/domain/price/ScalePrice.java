package com.city.parkingMeter.parking.domain.price;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ScalePrice {
    public static BigDecimal scale(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_UP);
    }
}
