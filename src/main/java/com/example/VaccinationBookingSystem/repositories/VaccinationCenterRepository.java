package com.example.VaccinationBookingSystem.repositories;

import com.example.VaccinationBookingSystem.enums.CenterType;
import com.example.VaccinationBookingSystem.modules.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter, Integer> {

    public VaccinationCenter findByCenterName(String name);

    public List<VaccinationCenter> findByCenterType(CenterType centerType);
}
