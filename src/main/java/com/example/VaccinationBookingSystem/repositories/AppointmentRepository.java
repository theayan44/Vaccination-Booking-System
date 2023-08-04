package com.example.VaccinationBookingSystem.repositories;

import com.example.VaccinationBookingSystem.modules.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query(value = "select * from appointment where person_id = :id", nativeQuery = true)
    List<Appointment> findByPersonId(int id);

    @Query(value = "select * from appointment where doctor_id = :id", nativeQuery = true)
    List<Appointment> findByDoctorId(int id);
}
