package com.musala.thedrone.repositories;

import com.musala.thedrone.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
