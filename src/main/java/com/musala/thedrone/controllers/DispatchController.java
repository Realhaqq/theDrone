package com.musala.thedrone.controllers;

import com.musala.thedrone.pojos.AddDroneRequest;
import com.musala.thedrone.sevices.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DispatchController {
    @Autowired
    private DispatchService dispatchService;

    @PostMapping("/drone/add")
    public ResponseEntity<?> addNewDrone(@RequestBody AddDroneRequest addDroneRequest) {
        return dispatchService.addNewDrone(addDroneRequest);
    }


}
