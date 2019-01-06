package com.city.parkingMeter.parking.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.isNull;

@Getter
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
        return of(UUID.fromString(id));
    }

    public static HashId of(UUID id) {
        if (isNull(id)) {
            throw new IllegalArgumentException("HashId must not be null");
        }

        return new HashId(id);
    }

    public String getValueAsString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashId hashId = (HashId) o;
        return value.equals(hashId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
