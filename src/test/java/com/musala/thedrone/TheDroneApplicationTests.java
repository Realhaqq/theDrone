package com.musala.thedrone;

import com.musala.thedrone.pojos.AddDroneRequest;
import com.musala.thedrone.pojos.LoadDroneRequest;
import com.musala.thedrone.pojos.MedicationItems;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TheDroneApplicationTests {

    @Test
    void contextLoads() {
    }

    @LocalServerPort
    private int port = 9090;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl = "http://localhost:" + port + "/";

    @Test
    public void testDroneCheck() {
        String droneCheckUrl = baseUrl + "api/v1/drone/check";

        try {
            ResponseEntity<String> result = restTemplate.getForEntity(droneCheckUrl, String.class);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).contains("Successful");
            assertThat(result.getBody()).contains("data").isNotEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // test fot all drone logs
    @Test
    public void testDroneLogs() {
        String droneLogsUrl = baseUrl + "api/v1/drone/logs";

        try {
            ResponseEntity<String> result = restTemplate.getForEntity(droneLogsUrl, String.class);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).contains("Successful");
            assertThat(result.getBody()).contains("data").isNotEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // test for check drone battery level
    @Test
    public void testDroneBatteryLevel() {
        String droneBatteryLevelUrl = baseUrl + "api/v1/drone/batteryLevel/DR-1650172660000";

        try {
            ResponseEntity<String> result = restTemplate.getForEntity(droneBatteryLevelUrl, String.class);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).contains("Successful");
            assertThat(result.getBody()).contains("data").isNotEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // test for add new drone with valid data and check if it is added, if not then return error, with body request and method
    @Test
    public void testAddNewDrone() {
        String addNewDroneUrl = baseUrl + "api/v1/drone/add";

        AddDroneRequest addDroneRequest = new AddDroneRequest();
        addDroneRequest.setModel("Cruiserweight");
        addDroneRequest.setBatteryCapacity(BigDecimal.valueOf(100));
        addDroneRequest.setWeightLimit(BigDecimal.valueOf(200));

        HttpHeaders headers = new HttpHeaders();
        headers.set("body", addDroneRequest.toString());
        headers.set("Content-Type", "application/json");

        HttpEntity<AddDroneRequest> request = new HttpEntity<>(addDroneRequest, headers);

        try {
            ResponseEntity<String> result = restTemplate.postForEntity(addNewDroneUrl, request, String.class);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody()).contains("Successful");
            assertThat(result.getBody()).contains("data").isNotEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // test for check loaded medication by drone serial number
    @Test
    public void testLoadedMedication() {
            String loadedMedicationUrl = baseUrl + "api/v1/drone/medication/DR-1650172660000";

            try {
                ResponseEntity<String> result = restTemplate.getForEntity(loadedMedicationUrl, String.class);
                assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(result.getBody()).contains("Successful");
                assertThat(result.getBody()).contains("data").isNotEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    // test for load medication to drone with valid data and check if it is loaded, if not then return error, with body request and method
    @Test
    public void testLoadMedication() {
        String loadMedicationUrl = baseUrl + "api/v1/drone/load";

        LoadDroneRequest loadDroneRequest = new LoadDroneRequest();
        loadDroneRequest.setDroneSerialNumber("DR-1650172660000");

        MedicationItems medicationItems = new MedicationItems();
        medicationItems.setCode("ME-LI");
        loadDroneRequest.setItems(Collections.singletonList(medicationItems));

        HttpHeaders headers = new HttpHeaders();
        headers.set("body", loadDroneRequest.toString());
        headers.set("Content-Type", "application/json");

        HttpEntity<LoadDroneRequest> request = new HttpEntity<>(loadDroneRequest, headers);

        try {
            ResponseEntity<String> result = restTemplate.postForEntity(loadMedicationUrl, request, String.class);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
//            assertThat(result.getBody()).contains("Successful");
//            assertThat(result.getBody()).contains("data").isNotEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
