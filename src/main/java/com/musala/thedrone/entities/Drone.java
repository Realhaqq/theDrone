package com.musala.thedrone.entities;

import com.musala.thedrone.enums.DroneState;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Drone {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String serialNumber;
    @Column(nullable = false, length = 100)
    private String model;
    @Column(nullable = false, length = 22)
    private BigDecimal weightLimit;
    @Column(nullable = false, length = 22)
    private BigDecimal batteryCapacity;
    @Enumerated(EnumType.STRING)
    private DroneState state;


    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void setUpdatedAt() {
        updatedAt = new Date();
    }


}
