package com.example.VaccinationBookingSystem.controllers;

import com.example.VaccinationBookingSystem.customExceptions.Dose1NotTakenException;
import com.example.VaccinationBookingSystem.customExceptions.Dose2NotTakenException;
import com.example.VaccinationBookingSystem.customExceptions.PersonNotFoundException;
import com.example.VaccinationBookingSystem.dto.responseDto.CertificateResponseDto;
import com.example.VaccinationBookingSystem.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    CertificateService certificateService;


    @GetMapping("/dose1")
    public ResponseEntity getDose1Certificate(@RequestParam("personEmail") String personEmail){
        try {
            CertificateResponseDto response = certificateService.getDose1Certificate(personEmail);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (PersonNotFoundException | Dose1NotTakenException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/dose2")
    public ResponseEntity getDose2Certificate(@RequestParam("personEmail") String personEmail){
        try {
            CertificateResponseDto response = certificateService.getDose2Certificate(personEmail);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (PersonNotFoundException | Dose2NotTakenException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
