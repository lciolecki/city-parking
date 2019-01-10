package com.city.parkingMeter.parking.domain;

import com.city.parkingMeter.parking.domain.dto.ParkingPayload;
import com.city.parkingMeter.parking.domain.dto.ParkingStopPayload;
import com.city.parkingMeter.parking.domain.vo.HashId;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Entity
@Getter
@Table(name = "parking")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parking {

    @EmbeddedId
    @ApiModelProperty(dataType = "java.util.UUID")
    private HashId id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverType driverType = DriverType.REGULAR;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ParkingStatus parkingStatus = ParkingStatus.STARTED;

    @Column(nullable = false)
    private RegistrationNumber registrationNumber;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant startedAt;

    private Instant finishedAt;

    @Column(precision = 10, scale = 2, columnDefinition = "Decimal(10,2)")
    private BigDecimal price;

    private Parking(
            HashId id,
            RegistrationNumber registrationNumber,
            DriverType driverType
    ) {
        this.registrationNumber = registrationNumber;
        this.driverType = driverType;
        this.id = id;
    }

    public static Parking createFromPayload(ParkingPayload payload) {
        return new Parking(new HashId(), payload.getRegistrationNumber(), payload.getDriverType());
    }

    public Parking finishParking(ParkingStopPayload payload) {
        this.parkingStatus = ParkingStatus.FINISHED;
        this.finishedAt = payload.getFinishedAt();
        this.price = payload.getPrice();

        return this;
    }

    public String getIdAsString() {
        return Optional.ofNullable(id)
                .map(HashId::getValueAsString)
                .orElse(null);
    }
}
