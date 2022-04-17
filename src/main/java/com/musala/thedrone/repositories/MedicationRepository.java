package com.musala.thedrone.repositories;

import com.musala.thedrone.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query(nativeQuery = true,
            value =  "SELECT * FROM Medication m WHERE m.code = ?1 ORDER BY m.code DESC LIMIT 1")
    Optional<Medication> findByCode(String code);
}
