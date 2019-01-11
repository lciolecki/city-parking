package com.city.parkingMeter.integration;

import com.city.parkingMeter.parking.domain.Parking;
import com.city.parkingMeter.parking.domain.vo.HashId;
import com.city.parkingMeter.parking.repository.ParkingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
public class ParkingIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ParkingRepository repository;

    @After
    public void clearDb() {
        repository.deleteAll();
    }

    private HttpHeaders getRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    protected String getRequesttData(String filename) throws IOException {
        URL url = Resources.getResource(String.format("json/%s", filename));
        return Resources.toString(url, StandardCharsets.UTF_8);
    }

    @Test
    public void shouldCreateParking() throws Exception {
        String parkingData = getRequesttData("parking.json");

        String content = mvc.perform(post("/parking/").content(parkingData).headers(getRequestHeaders()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ParkingCreateResponseWrapper response = objectMapper.readValue(content, ParkingCreateResponseWrapper.class);
        HashId id = HashId.of(response.id);

        Optional<Parking> parkingOptional = repository.findById(id);

        Assert.assertTrue(parkingOptional.isPresent());

        Parking parking = parkingOptional.get();

        Assert.assertEquals(response.driverType, parking.getDriverType().toString());
        Assert.assertEquals(response.registrationNumber, parking.getRegistrationNumber().toString());
    }

    @Test
    public void shouldNotCreateParkingWithSameRegistrationNumber() throws Exception {
        String parkingData = getRequesttData("parking.json");

        mvc.perform(post("/parking/").content(parkingData).headers(getRequestHeaders()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        mvc.perform(post("/parking/").content(parkingData).headers(getRequestHeaders()))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private static class ParkingCreateResponseWrapper {

        public String registrationNumber;

        public String driverType;

        public String id;

    }
}
