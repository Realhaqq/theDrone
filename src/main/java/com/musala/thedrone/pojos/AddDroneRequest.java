package com.musala.thedrone.pojos;

import com.musala.thedrone.enums.DroneState;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddDroneRequest {
    private String model;
    private BigDecimal weightLimit;
    private BigDecimal batteryCapacity;
    private String state;
}
