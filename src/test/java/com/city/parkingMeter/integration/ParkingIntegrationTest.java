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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

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

    protected String getRequestData(String filename) throws IOException {
        URL url = Resources.getResource(String.format("json/%s", filename));
        return Resources.toString(url, StandardCharsets.UTF_8);
    }

    protected String doRequest(HttpMethod httpMethod, String url, String data, ResultMatcher exceptedStatus) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.request(httpMethod, url);
        requestBuilder.content(data).headers(getRequestHeaders());

        return mvc.perform(requestBuilder)
                .andExpect(exceptedStatus)
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private static class ParkingCreateResponseWrapper {

        public String registrationNumber;

        public String driverType;

        public String id;

    }

    @Test
    public void shouldCreateParking() throws Exception {
        String parkingData = getRequestData("parking.json");
        String content = doRequest(HttpMethod.POST, "/parking/", parkingData, status().isCreated());
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
        String parkingData = getRequestData("parking.json");
        doRequest(HttpMethod.POST, "/parking/", parkingData, status().isCreated());
        doRequest(HttpMethod.POST, "/parking/", parkingData, status().isBadRequest());
    }
}
