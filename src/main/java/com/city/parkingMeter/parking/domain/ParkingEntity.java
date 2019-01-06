package com.city.parkingMeter.parking.domain;

import com.city.parkingMeter.parking.domain.vo.HashId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Table(name = "parking")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingEntity {

    @EmbeddedId
    @ApiModelProperty(dataType = "java.util.UUID")
    private HashId id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverType driverType = DriverType.REGULAR;

    private String registrationNumber;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant startAt;

    private Instant finishedAt;

    @Column(precision=10, scale=2, columnDefinition="Decimal(10,2)")
    private BigDecimal price;

    private ParkingEntity(
            HashId id,
            String registrationNumber,
            DriverType driverType
    ) {
        this.registrationNumber = registrationNumber;
        this.driverType = driverType;
        this.id = id;
    }

    public ParkingEntity create(String registrationNumber, DriverType driverType) {
        return new ParkingEntity(new HashId(), registrationNumber, driverType);
    }

    public boolean isRegular() {
        return driverType.equals(DriverType.REGULAR);
    }
}
