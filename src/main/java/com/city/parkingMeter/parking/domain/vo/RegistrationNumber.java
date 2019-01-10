package com.city.parkingMeter.parking.domain.vo;

import com.city.parkingMeter.infrastructure.exception.InvalidValueObjectDataException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@EqualsAndHashCode
public final class RegistrationNumber implements Serializable {

    private static final java.util.regex.Pattern REGISTRATION_NUMBER_PATTERN = java.util.regex.Pattern.compile("^[A-Z0-9]+");

    @JsonValue
    private final String value;

    private RegistrationNumber(String value) {
        this.value = value;
    }

    @JsonCreator
    public static RegistrationNumber of(@JsonProperty("registrationNumber") String registrationNumber) {
        RegistrationNumber.validate(registrationNumber);
        return new RegistrationNumber(registrationNumber);
    }

    private static String validate(String value) {
        if (isBlank(value) || !REGISTRATION_NUMBER_PATTERN.matcher(value).matches()) {
            throw InvalidValueObjectDataException.invalid(RegistrationNumber.class, value);
        }

        return value;
    }

    @Override
    public String toString() { return value; }
}
