package com.musala.thedrone.repositories;

import com.musala.thedrone.entities.Drone;
import com.musala.thedrone.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    List<Drone> findAllByState(DroneState idle);
}
