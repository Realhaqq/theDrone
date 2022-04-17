package com.musala.thedrone.sevices;

import com.musala.thedrone.entities.Drone;
import com.musala.thedrone.entities.LoadedDrone;
import com.musala.thedrone.entities.Medication;
import com.musala.thedrone.enums.DroneState;
import com.musala.thedrone.pojos.AddDroneRequest;
import com.musala.thedrone.pojos.ApiResponse;
import com.musala.thedrone.pojos.LoadDroneRequest;
import com.musala.thedrone.pojos.MedicationItems;
import com.musala.thedrone.repositories.DroneRepository;
import com.musala.thedrone.repositories.LoadedDroneRepository;
import com.musala.thedrone.repositories.MedicationRepository;
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

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    LoadedDroneRepository loadedDroneRepository;

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

    public ResponseEntity<?> loadDrone(LoadDroneRequest loadDroneRequest) {
        // check if drone is available
        Optional<Drone> drone = droneRepository.findBySerialNumber(loadDroneRequest.getDroneSerialNumber());
        if (!drone.isPresent())
            return ResponseEntity.ok(new ApiResponse(false, "Drone not found", 101, loadDroneRequest.getDroneSerialNumber()));

        // Prevent the drone from being in LOADING state if the battery level is below 25%
        if (drone.get().getBatteryCapacity().compareTo(BigDecimal.valueOf(25)) < 0)
            return ResponseEntity.ok(new ApiResponse(false, "Drone battery level is below 25%", 101, loadDroneRequest.getDroneSerialNumber()));


        // check if drone is in LOADED state
        if (drone.get().getState() == DroneState.LOADED)
            return ResponseEntity.ok(new ApiResponse(false, "Drone is already loaded", 101, loadDroneRequest.getDroneSerialNumber()));

        // update drone state to LOADING
        drone.get().setState(DroneState.LOADING);
        droneRepository.save(drone.get());

        for (MedicationItems medicationItems : loadDroneRequest.getItems()) {
            // check if medication is available
            Optional<Medication> medication = medicationRepository.findByCode(medicationItems.getCode());
            if (!medication.isPresent())
                return ResponseEntity.ok(new ApiResponse(false, "Medication not found for " + medicationItems.getCode(), 101, medicationItems.getCode()));

            // Prevent the drone from being loaded with more weight that it can carry
            if (medication.get().getWeight().compareTo(medication.get().getWeight()) > 0)
                return ResponseEntity.ok(new ApiResponse(false, "Medication weight is more than the drone can carry for " + medicationItems.getCode() , 101, medicationItems.getCode()));


            // insert loaded drone

            // check if item is loaded
            Optional<LoadedDrone> loadedDrone = loadedDroneRepository.findBySerialNumberAndMedicationCode(loadDroneRequest.getDroneSerialNumber(), medicationItems.getCode());
            if (!loadedDrone.isPresent()) {
                // insert new loaded drone
                LoadedDrone loadedDrone1 = new LoadedDrone();
                loadedDrone1.setSerialNumber(loadDroneRequest.getDroneSerialNumber());
                loadedDrone1.setMedicationCode(medicationItems.getCode());
                loadedDroneRepository.save(loadedDrone1);
            }

        }


        // update drone state to LOADED
        drone.get().setState(DroneState.LOADED);
        droneRepository.save(drone.get());


        return ResponseEntity.ok(new ApiResponse(true, "Drone Loaded Successful", 100, drone));
    }

    public ResponseEntity<?> checkMedication(String serialNumber) {
        // checking loaded medication items for a given drone
        List<LoadedDrone> loadedDrone = loadedDroneRepository.findBySerialNumber(serialNumber);
        if (loadedDrone.isEmpty())
            return ResponseEntity.ok(new ApiResponse(false, "Drone not loaded or not found", 101, serialNumber));


        // get medication details
        List<Medication> medications = new ArrayList<>();
        for (LoadedDrone loadedDrone1 : loadedDrone) {
            Optional<Medication> medication = medicationRepository.findByCode(loadedDrone1.getMedicationCode());
            if (medication.isPresent())
                medications.add(medication.get());
        }

        return ResponseEntity.ok(new ApiResponse(true, "Successful", 100, medications));
    }
}
