package com.example.VaccinationBookingSystem.repositories;

import com.example.VaccinationBookingSystem.modules.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    public Doctor findByEmail(String email);

    @Query(value = "select d from Doctor d where d.age > :age")
    public List<Doctor> ageGreaterThanX(int age);
}
