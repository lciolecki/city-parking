package com.city.parkingMeter.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidValueObjectDataException extends RuntimeException {

    public InvalidValueObjectDataException(String s) {
        super(s);
    }

    public static InvalidValueObjectDataException invalid(String message) {
        throw new InvalidValueObjectDataException(message);
    }

    public static InvalidValueObjectDataException invalid(Class<?> objectClass, String value) {
        return new InvalidValueObjectDataException(String.format(
                "Value %s is not valid %s object.",
                value,
                objectClass.getSimpleName()
        ));
    }

    public static InvalidValueObjectDataException invalid(Class<?> objectClass, Long value) {
        return new InvalidValueObjectDataException(String.format(
                "Value %s is not valid %s object.",
                value.toString(),
                objectClass.getSimpleName()
        ));
    }

    public static InvalidValueObjectDataException nullException(Class<?> objectClass) {
        return new InvalidValueObjectDataException(String.format(
                "Value null is not valid %s object.",
                objectClass.getSimpleName()
        ));
    }
}
