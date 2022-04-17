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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DispatchService {

    @Autowired
    DroneRepository droneRepository;
    public ResponseEntity<?> addNewDrone(AddDroneRequest addDroneRequest) {

        if (addDroneRequest.getWeightLimit().compareTo(BigDecimal.valueOf(500)) > 0)
            return ResponseEntity.ok(new ApiResponse(false, "Weight limit cannot be more than 500", 101, new ArrayList<>()));


        if (addDroneRequest.getBatteryCapacity().compareTo(BigDecimal.valueOf(100)) > 0)
            return ResponseEntity.ok(new ApiResponse(false, "Battery capacity cannot be more than 100%", 101, new ArrayList<>()));


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
        List<Drone> drones = droneRepository.findAllByState(DroneState.IDLE);
        if (drones.isEmpty())
            return ResponseEntity.ok(new ApiResponse(false, "No drones available", 101, new ArrayList<>()));

        return ResponseEntity.ok(new ApiResponse(true, "Successful", 100, drones));
    }

    public ResponseEntity<?> checkBatteryLevel(String serialNumber) {
        Optional<Drone> drone = droneRepository.findBySerialNumber(serialNumber);
        if (!drone.isPresent())
            return ResponseEntity.ok(new ApiResponse(false, "Drone not found", 101, serialNumber));

        return ResponseEntity.ok(new ApiResponse(true, "Successful", 100, drone));

    }
}
