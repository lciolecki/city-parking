package com.city.parkingMeter.parking.domain.price;

import com.city.parkingMeter.parking.domain.DriverType;
import com.city.parkingMeter.parking.domain.exception.InvalidDriverType;

public final class PriceCounterFactory {
    public static PriceCalculator create(DriverType driverType) {
        switch (driverType) {
            case REGULAR:
                return new RegularPriceCalculator();
            case DISABLED:
                return new DisabledPriceCalculator();
            default:
                throw InvalidDriverType.of(driverType);
        }
    }
}
