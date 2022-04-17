package com.musala.thedrone.repositories;

import com.musala.thedrone.entities.Drone;
import com.musala.thedrone.entities.DroneLog;
import com.musala.thedrone.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DroneLogRepository extends JpaRepository<DroneLog, Long> {

}
