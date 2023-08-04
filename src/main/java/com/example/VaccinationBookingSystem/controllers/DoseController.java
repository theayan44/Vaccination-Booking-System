package com.example.VaccinationBookingSystem.controllers;

import com.example.VaccinationBookingSystem.customExceptions.Dose1NotTakenException;
import com.example.VaccinationBookingSystem.customExceptions.DoseAlreadyTakenException;
import com.example.VaccinationBookingSystem.customExceptions.InsufficientInputException;
import com.example.VaccinationBookingSystem.customExceptions.PersonNotFoundException;
import com.example.VaccinationBookingSystem.dto.responseDto.TakeDose1ResponseDto;
import com.example.VaccinationBookingSystem.enums.DoseType;
import com.example.VaccinationBookingSystem.modules.Dose;
import com.example.VaccinationBookingSystem.services.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dose")
public class DoseController {
    @Autowired
    DoseService doseService;

    @PostMapping("/take-dose1")
    public ResponseEntity takeDose1(@RequestParam("personId") int personId, @RequestParam("doseType") DoseType doseType){
        try {
            TakeDose1ResponseDto doseTaken = doseService.takeDose1(personId, doseType);
            return new ResponseEntity<>("Congratulation! You have completed your first dose. Confirmation mail has been sent to your registered Email Id\n" + doseTaken, HttpStatus.CREATED);
        }catch (PersonNotFoundException | DoseAlreadyTakenException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/take-dose2")
    public ResponseEntity takeDose2(@RequestParam("personId") int personId, @RequestParam("doseType") DoseType doseType){
        try {
            TakeDose1ResponseDto doseTaken = doseService.takeDose2(personId, doseType);
            return new ResponseEntity<>("Congratulation! You have completed your second dose. Confirmation mail has been sent to your registered Email Id\n" + doseTaken, HttpStatus.CREATED);
        }catch (PersonNotFoundException | Dose1NotTakenException | DoseAlreadyTakenException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
