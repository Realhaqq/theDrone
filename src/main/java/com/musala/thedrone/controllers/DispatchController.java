package com.musala.thedrone.controllers;

import com.musala.thedrone.pojos.AddDroneRequest;
import com.musala.thedrone.pojos.LoadDroneRequest;
import com.musala.thedrone.sevices.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DispatchController {

    @Autowired
    private DispatchService dispatchService;

    @PostMapping("/drone/add")
    public ResponseEntity<?> addNewDrone(@RequestBody AddDroneRequest addDroneRequest) {
        return dispatchService.addNewDrone(addDroneRequest);
    }

    @GetMapping("/drone/check")
    public ResponseEntity<?> checkAvailableDrones() {
        return dispatchService.checkAvailableDrones();
    }

    @GetMapping("/drone/batteryLevel/{serialNumber}")
    public ResponseEntity<?> checkBatteryLevel(@PathVariable String serialNumber) {
        return dispatchService.checkBatteryLevel(serialNumber);
    }

    @PostMapping("/drone/load")
    public ResponseEntity<?> loadDrone(@RequestBody LoadDroneRequest loadDroneRequest) {
        return dispatchService.loadDrone(loadDroneRequest);
    }

    @GetMapping("/drone/medication/{serialNumber}")
    public ResponseEntity<?> checkMedication(@PathVariable String serialNumber) {
        return dispatchService.checkMedication(serialNumber);
    }


    @GetMapping("/drone/logs")
    public ResponseEntity<?> getDroneLogs() {
        return dispatchService.getDroneLogs();
    }



}
