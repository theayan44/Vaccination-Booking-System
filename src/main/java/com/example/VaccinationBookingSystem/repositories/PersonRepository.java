package com.example.VaccinationBookingSystem.repositories;

import com.example.VaccinationBookingSystem.enums.Gender;
import com.example.VaccinationBookingSystem.modules.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByEmail(String oldEmail);

    List<Person> findByGender(Gender gender);

    List<Person> findByDose1TakenAndDose2Taken(boolean dose1Taken,boolean dose2Taken);

    List<Person> findByDose1TakenAndDose2TakenAndGender(boolean dose1Taken,boolean dose2Taken, Gender gender);
}
