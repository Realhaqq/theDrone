package com.musala.thedrone.sevices;

import com.musala.thedrone.entities.Drone;
import com.musala.thedrone.enums.DroneState;
import com.musala.thedrone.pojos.AddDroneRequest;
import com.musala.thedrone.pojos.ApiResponse;
import com.musala.thedrone.repositories.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DispatchService {

    @Autowired
    DroneRepository droneRepository;
    public ResponseEntity<?> addNewDrone(AddDroneRequest addDroneRequest) {

        if (addDroneRequest.getWeightLimit().compareTo(BigDecimal.valueOf(500)) > 0)
            return ResponseEntity.ok(new ApiResponse(false, "Weight limit cannot be more than 500", 101, addDroneRequest));


        if (addDroneRequest.getBatteryCapacity().compareTo(BigDecimal.valueOf(100)) > 0)
            return ResponseEntity.ok(new ApiResponse(false, "Battery capacity cannot be more than 100%", 101, addDroneRequest));




        String serialNumber = System.currentTimeMillis() + "";

        Drone drone = new Drone();
        drone.setSerialNumber("DR-" + serialNumber);
        drone.setWeightLimit(addDroneRequest.getWeightLimit());
        drone.setBatteryCapacity(addDroneRequest.getBatteryCapacity());
        drone.setModel(addDroneRequest.getModel());
        drone.setState(DroneState.IDLE);

        droneRepository.save(drone);

        return ResponseEntity.ok(new ApiResponse(true, "Drone Added Successful", 100, drone));



    }

    public ResponseEntity<?> checkAvailableDrones() {
        return ResponseEntity.ok(new ApiResponse(true, "Available Drones", 100, droneRepository.findAllByState(DroneState.IDLE)));

    }
}
