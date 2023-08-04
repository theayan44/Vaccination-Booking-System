package com.example.VaccinationBookingSystem.controllers;

import com.example.VaccinationBookingSystem.customExceptions.DoctorNotFoundException;
import com.example.VaccinationBookingSystem.customExceptions.EmailIdAlreadyExistsException;
import com.example.VaccinationBookingSystem.customExceptions.InsufficientInputException;
import com.example.VaccinationBookingSystem.customExceptions.VaccinationCenterNotFoundException;
import com.example.VaccinationBookingSystem.dto.requestDto.AddDoctorRequestDto;
import com.example.VaccinationBookingSystem.dto.requestDto.DoctorsEmailOrAgeUpdateRequestDto;
import com.example.VaccinationBookingSystem.dto.responseDto.DoctorResponseDto;
import com.example.VaccinationBookingSystem.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity addDoctor(@RequestBody AddDoctorRequestDto doctorRequestDto){
        try{
            DoctorResponseDto doctorResponse = doctorService.addDoctor(doctorRequestDto);
            return new ResponseEntity<>(doctorResponse, HttpStatus.CREATED);
        }catch(VaccinationCenterNotFoundException | EmailIdAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor-with-highest-appointments")
    public ResponseEntity doctorWithHighestAppointments(){
        try {
            List<DoctorResponseDto> doctorsList = doctorService.doctorWithHighestAppointments();
            return new ResponseEntity<>(doctorsList, HttpStatus.FOUND);
        }catch(DoctorNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctor-greater-than-certain-age/{age}")
    public ResponseEntity doctorsGreaterThanCertainAge(@PathVariable("age") int age){
        try {
            List<DoctorResponseDto> doctorsList = doctorService.doctorsGreaterThanCertainAge(age);
            return new ResponseEntity<>(doctorsList, HttpStatus.FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-email-or-age")
    public ResponseEntity updateEmailOrAge(@RequestBody DoctorsEmailOrAgeUpdateRequestDto doctorsEmailOrAgeUpdateRequestDto){
        try {
            DoctorResponseDto doctorResponse = doctorService.updateEmailOrAge(doctorsEmailOrAgeUpdateRequestDto);
            return new ResponseEntity<>(doctorResponse, HttpStatus.ACCEPTED);
        }catch(InsufficientInputException | DoctorNotFoundException | EmailIdAlreadyExistsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
