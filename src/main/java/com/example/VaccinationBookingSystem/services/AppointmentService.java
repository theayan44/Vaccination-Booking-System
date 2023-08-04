package com.example.VaccinationBookingSystem.services;

import com.example.VaccinationBookingSystem.Transformer.AppointmentTransformer;
import com.example.VaccinationBookingSystem.customExceptions.DoctorNotFoundException;
import com.example.VaccinationBookingSystem.customExceptions.PersonNotFoundException;
import com.example.VaccinationBookingSystem.dto.responseDto.AppointmentResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.DoctorAppointmentResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.PersonAppointmentResponseDto;
import com.example.VaccinationBookingSystem.modules.Appointment;
import com.example.VaccinationBookingSystem.modules.Doctor;
import com.example.VaccinationBookingSystem.modules.Person;
import com.example.VaccinationBookingSystem.repositories.AppointmentRepository;
import com.example.VaccinationBookingSystem.repositories.DoctorRepository;
import com.example.VaccinationBookingSystem.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    DoctorRepository doctorRepository;


    public AppointmentResponseDto bookAppointment(int personId, int doctorId) {
        // check if person exists or not in DB
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if(optionalPerson.isEmpty()){
            throw new PersonNotFoundException("Person Id Doesn't Exists");
        }

        // check if doctor exists or not in DB
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if(optionalDoctor.isEmpty()){
            throw new DoctorNotFoundException("Doctor Id Doesn't Exists");
        }

        // prepare Appointment entity and save into DB
        Appointment appointment = AppointmentTransformer.getAppointmentEntity(optionalPerson.get(), optionalDoctor.get());
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // now update in person entity
        Person person = optionalPerson.get();
        List<Appointment> appointmentListOfPerson = person.getAppointmentList();
        appointmentListOfPerson.add(savedAppointment);
        person.setAppointmentList(appointmentListOfPerson);
        personRepository.save(person);

        // now update in doctor entity
        Doctor doctor = optionalDoctor.get();
        List<Appointment> appointmentListOfDoctor = doctor.getAppointmentList();
        appointmentListOfDoctor.add(savedAppointment);
        doctor.setAppointmentList(appointmentListOfDoctor);
        doctorRepository.save(doctor);

        // Appointment Entity -> AppointmentResponseDto
        return AppointmentTransformer.appointmentToAppointmentResponseDto(savedAppointment);
    }


    public List<PersonAppointmentResponseDto> allAppointmentsOfPerson(int id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if(optionalPerson.isEmpty()){
            throw new PersonNotFoundException("Person Id Doesn't Exist");
        }

        List<Appointment> appointmentList = appointmentRepository.findByPersonId(id);
        List<PersonAppointmentResponseDto> ansList = new ArrayList<>();
        for(Appointment appointment : appointmentList){
            PersonAppointmentResponseDto personAppointmentResponseDto = AppointmentTransformer.appointmentToPersonAppointmentResponseDto(appointment);
            ansList.add(personAppointmentResponseDto);
        }

        return ansList;
    }


    public List<DoctorAppointmentResponseDto> allAppointmentsOfDoctor(int id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if(optionalDoctor.isEmpty()){
            throw new PersonNotFoundException("Doctor Id Doesn't Exist");
        }

        List<Appointment> appointmentList = appointmentRepository.findByDoctorId(id);
        List<DoctorAppointmentResponseDto> ansList = new ArrayList<>();
        for(Appointment appointment : appointmentList){
            DoctorAppointmentResponseDto doctorAppointmentResponseDto = AppointmentTransformer.appointmentToDoctorAppointmentResponseDto(appointment);
            ansList.add(doctorAppointmentResponseDto);
        }

        return ansList;
    }

}
