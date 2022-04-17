package com.musala.thedrone.repositories;

import com.musala.thedrone.entities.LoadedDrone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoadedDroneRepository extends JpaRepository<LoadedDrone, Long> {

    Optional<LoadedDrone> findBySerialNumberAndMedicationCode(String serialNumber, String code);

    List<LoadedDrone> findBySerialNumber(String serialNumber);
}
