package com.example.VaccinationBookingSystem.controllers;


import com.example.VaccinationBookingSystem.customExceptions.NoCenterFoundException;
import com.example.VaccinationBookingSystem.customExceptions.VaccinationCenterAlreadyExistException;
import com.example.VaccinationBookingSystem.customExceptions.VaccinationCenterNotFoundException;
import com.example.VaccinationBookingSystem.dto.requestDto.AddVaccinationCenterRequestDto;
import com.example.VaccinationBookingSystem.dto.responseDto.NoOFDoctorsInACenterResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.VaccinationCenterResponseDto;
import com.example.VaccinationBookingSystem.enums.CenterType;
import com.example.VaccinationBookingSystem.services.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccination-center")
public class VaccinationCenterController {

    @Autowired
    VaccinationCenterService vaccinationCenterService;

    @PostMapping("/add")
    public ResponseEntity addVaccinationCenter(@RequestBody AddVaccinationCenterRequestDto vaccinationCenterRequestDto){
        try {
            VaccinationCenterResponseDto vaccinationCenterResponse = vaccinationCenterService.addVaccinationCenter(vaccinationCenterRequestDto);
            return new ResponseEntity<>(vaccinationCenterResponse, HttpStatus.CREATED);
        }catch(VaccinationCenterAlreadyExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-doctors-of-a-center")
    public ResponseEntity allDoctorsOfAParticularCenter(@RequestParam("centerName") String centerName){
        try {
            List<String> doctorsList = vaccinationCenterService.allDoctorsOfAParticularCenter(centerName);
            return new ResponseEntity<>(doctorsList, HttpStatus.FOUND);
        }catch(VaccinationCenterNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-doctors-of-particular-center-type/{centerType}")
    public ResponseEntity allDoctorsOfParticularCenterType(@PathVariable("centerType") CenterType centerType){
        try {
            List<String> allDoctors = vaccinationCenterService.allDoctorsOfParticularCenterType(centerType);
            return new ResponseEntity<>(allDoctors, HttpStatus.FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/center-with-highest-doctors")
    public ResponseEntity vaccinationCenterWithHighestDoctors(){
        try {
            List<NoOFDoctorsInACenterResponseDto> doctorsOfACenter = vaccinationCenterService.vaccinationCenterWithHighestDoctors();
            return new ResponseEntity<>(doctorsOfACenter, HttpStatus.FOUND);
        }catch(NoCenterFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/center-of-particular-type-with-highest-doctors/{centerType}")
    public ResponseEntity vaccinationCenterOfParticularTypeWithHighestDoctors(@PathVariable("centerType") CenterType centerType){
        try {
            List<NoOFDoctorsInACenterResponseDto> doctorsOfACenter = vaccinationCenterService.vaccinationCenterOfParticularTypeWithHighestDoctors(centerType);
            return new ResponseEntity<>(doctorsOfACenter, HttpStatus.FOUND);
        }catch(NoCenterFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
