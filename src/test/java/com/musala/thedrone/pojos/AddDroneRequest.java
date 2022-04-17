package com.musala.thedrone.pojos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddDroneRequest {
    private String model;
    private BigDecimal weightLimit;
    private BigDecimal batteryCapacity;
    private String state;
}
