package com.example.VaccinationBookingSystem.repositories;

import com.example.VaccinationBookingSystem.modules.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
}
