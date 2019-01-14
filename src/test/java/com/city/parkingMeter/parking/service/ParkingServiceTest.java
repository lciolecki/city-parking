package com.city.parkingMeter.parking.service;

import com.city.parkingMeter.parking.domain.DriverType;
import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.dto.ParkingPayload;
import com.city.parkingMeter.parking.domain.vo.HashId;
import com.city.parkingMeter.parking.domain.vo.RegistrationNumber;
import com.city.parkingMeter.parking.repository.ParkingRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class ParkingServiceTest {

    @Mock
    ParkingRepository repository;

    ParkingService service;

    @Before
    public void setUp() {
        repository = mock(ParkingRepository.class);
        service = new ParkingService(repository);
    }

    private static Parking createExampleParkingEntity(RegistrationNumber registrationNumber, DriverType driverType) {
        ParkingPayload payload = new ParkingPayload(registrationNumber, driverType);
        return Parking.createFromPayload(payload);
    }

    @Test
    public void fetchByHashId() {
        RegistrationNumber registrationNumber = RegistrationNumber.of("LLB1234");
        Parking parking = createExampleParkingEntity(registrationNumber, DriverType.REGULAR);

        given(repository.findById(any())).willReturn(Optional.of(parking));
        Parking result = service.fetchById(new HashId());

        Assert.assertEquals(parking.getId(), result.getId());
    }

    @Test
    public void isVehicleStartedParkingTrue() {
        RegistrationNumber registrationNumber = RegistrationNumber.of("LLB1234");
        Parking parking = createExampleParkingEntity(registrationNumber, DriverType.REGULAR);

        given(repository.fetchStartedByRegistrationNumber(any())).willReturn(Optional.of(parking));
        boolean isStarted = service.hasVehicleStartedParking(registrationNumber);

        Assert.assertTrue(isStarted);
    }


    @Test
    public void isVehicleStartedParkingFalse() {
        RegistrationNumber registrationNumber = RegistrationNumber.of("LLB1234");
        boolean isStarted = service.hasVehicleStartedParking(registrationNumber);

        Assert.assertFalse(isStarted);
    }
}
