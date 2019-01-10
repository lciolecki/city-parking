package com.city.parkingMeter.parking.service;

import com.city.parkingMeter.parking.domain.DriverType;
import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.dto.ParkingPayload;
import com.city.parkingMeter.parking.domain.vo.HashId;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import com.city.parkingMeter.parking.repository.ParkingRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingServiceTest {

    @Test
    public void fetchByHashId() {
        ParkingPayload payload = new ParkingPayload(RegistrationNumber.of("LLB1234"), DriverType.REGULAR);
        Parking parking = Parking.createFromPayload(payload);

        ParkingRepository repository = mock(ParkingRepository.class);
        when(repository.findById(any())).thenReturn(Optional.of(parking));

        ParkingService service = new ParkingService(repository);
        Parking result = service.fetchById(new HashId());

        Assert.assertEquals(parking.getId(), result.getId());
    }

    @Test
    public void isVehicleStartedParkingTrue() {
        RegistrationNumber registrationNumber = RegistrationNumber.of("LLB1234");
        ParkingPayload payload = new ParkingPayload(registrationNumber, DriverType.REGULAR);
        Parking parking = Parking.createFromPayload(payload);

        ParkingRepository repository = mock(ParkingRepository.class);
        when(repository.fetchStartedByRegistrationNumber(any())).thenReturn(Optional.of(parking));

        ParkingService service = new ParkingService(repository);

        boolean isStarted = service.isVehicleStartedParking(registrationNumber);

        Assert.assertTrue(isStarted);
    }


    @Test
    public void isVehicleStartedParkingFalse() {
        RegistrationNumber registrationNumber = RegistrationNumber.of("LLB1234");
        ParkingRepository repository = mock(ParkingRepository.class);
        when(repository.fetchStartedByRegistrationNumber(any())).thenReturn(Optional.empty());

        ParkingService service = new ParkingService(repository);

        boolean isStarted = service.isVehicleStartedParking(registrationNumber);

        Assert.assertFalse(isStarted);
    }
}
