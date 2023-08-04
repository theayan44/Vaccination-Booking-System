package com.example.VaccinationBookingSystem.repositories;

import com.example.VaccinationBookingSystem.modules.Dose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoseRepository extends JpaRepository<Dose, Integer> {
}
