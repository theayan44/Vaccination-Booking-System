package com.example.VaccinationBookingSystem.controllers;

import com.example.VaccinationBookingSystem.customExceptions.DoctorNotFoundException;
import com.example.VaccinationBookingSystem.customExceptions.PersonNotFoundException;
import com.example.VaccinationBookingSystem.dto.responseDto.AppointmentResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.DoctorAppointmentResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.PersonAppointmentResponseDto;
import com.example.VaccinationBookingSystem.modules.Appointment;
import com.example.VaccinationBookingSystem.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity bookAppointment(@RequestParam("personId") int personId, @RequestParam("doctorId") int doctorId){
        try {
            AppointmentResponseDto bookedAppointment = appointmentService.bookAppointment(personId, doctorId);
            return new ResponseEntity<>(bookedAppointment, HttpStatus.CREATED);
        }catch(PersonNotFoundException | DoctorNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/person-appointments")
    public ResponseEntity allAppointmentsOfPerson(@RequestParam("id") int id){
        try {
            List<PersonAppointmentResponseDto> appointmentList = appointmentService.allAppointmentsOfPerson(id);
            return new ResponseEntity<>(appointmentList, HttpStatus.FOUND);
        }catch(PersonNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor-appointments")
    public ResponseEntity allAppointmentsOfDoctor(@RequestParam("id") int id){
        try {
            List<DoctorAppointmentResponseDto> appointmentList = appointmentService.allAppointmentsOfDoctor(id);
            return new ResponseEntity<>(appointmentList, HttpStatus.FOUND);
        }catch(PersonNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
