package com.musala.thedrone.pojos;

import com.musala.thedrone.entities.Medication;
import lombok.Data;

import java.util.List;

@Data
public class LoadDroneRequest {
    private String droneSerialNumber;
    private List<MedicationItems> items;
}
