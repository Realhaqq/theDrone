package com.musala.thedrone.cron;

import com.musala.thedrone.entities.Drone;
import com.musala.thedrone.entities.DroneLog;
import com.musala.thedrone.repositories.DroneLogRepository;
import com.musala.thedrone.repositories.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleTask {
    // Introduce a periodic task to check drones battery levels and create history/audit event log for this
    Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    DroneLogRepository droneLogRepository;

    @Autowired
    DroneRepository droneRepository;


    @Scheduled(cron = "0 0/10 * * * *", zone = "Europe/Sofia")
    public void checkDronesBatteryLevels() {
        List<Drone> drone = droneRepository.findAll();
        for (Drone d : drone) {
            DroneLog droneLog = new DroneLog();
            droneLog.setSerialNumber(d.getSerialNumber());
            droneLog.setBatteryLevel(d.getBatteryCapacity());
            droneLog.setEventType("Battery level check");
            droneLog.setEventDate(new java.util.Date());
            droneLogRepository.save(droneLog);
            logger.info("Drone battery level is: " + d.getBatteryCapacity());
        }

        logger.info("Checking drone battery levels...");
    }

}
