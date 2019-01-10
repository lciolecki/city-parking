package com.city.parkingMeter.parking.domain;

public enum DriverType {
    REGULAR, DISABLED;

    public static DriverType of(final String type) {
        return DriverType.valueOf(type.replace("-", "_").toUpperCase());
    }
}
