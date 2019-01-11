package com.city.parkingMeter.infrastructure.exception;

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

    public static <ID> RecordNotFoundException notFound(Class<?> objectClass, ID value) {
        return new RecordNotFoundException(
                String.format(
                        "Record of %s with %s doesn't exist.",
                        objectClass.getSimpleName(),
                        value.toString()
                )
        );
    }
}


