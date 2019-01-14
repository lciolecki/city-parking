package com.city.parkingMeter.parking.domain.vo;

import com.city.parkingMeter.infrastructure.exception.InvalidValueObjectDataException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Getter
@EqualsAndHashCode
public final class HashId implements Serializable {

    @NotNull
    @JsonValue
    @Column(name = "id")
    private final UUID value;

    private HashId(UUID value) {
        this.value = value;
    }

    public HashId() {
        value = UUID.randomUUID();
    }

    public static HashId of(String id) {
        return Optional.ofNullable(id)
                .map(v -> new HashId(UUID.fromString(id)))
                .orElseThrow(() -> InvalidValueObjectDataException.invalid("Invalid id."));
    }

    public static HashId of(UUID id) {
        return new HashId(HashId.validate(id));
    }

    public static UUID validate(UUID value) {
        if (isNull(value)) {
            throw InvalidValueObjectDataException.nullException(HashId.class);
        }

        return value;
    }

    public String getValueAsString() {
        return value.toString();
    }
}
