package com.musala.thedrone.repositories;

import com.musala.thedrone.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {

}
