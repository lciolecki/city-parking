package com.city.parkingMeter.infrastructure.exception;

import com.city.parkingMeter.parking.domain.vo.HashId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

    private RecordNotFoundException(String s) {
        super(s);
    }

    public static RecordNotFoundException notFound(String message) {
        return new RecordNotFoundException(message);
    }

    public static RecordNotFoundException notFound(Class<?> objectClass, String identifier, String value) {
        return new RecordNotFoundException(
                String.format(
                        "Record of %s with %s %s doesn't exist.",
                        objectClass.getSimpleName(),
                        identifier,
                        value
                )
        );
    }

    public static RecordNotFoundException notFound(Class<?> objectClass, HashId id) {
        return RecordNotFoundException.notFound(objectClass, "id", id.getValueAsString());
    }
}


