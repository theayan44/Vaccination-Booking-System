package com.example.VaccinationBookingSystem.controllers;

import com.example.VaccinationBookingSystem.customExceptions.InsufficientInputException;
import com.example.VaccinationBookingSystem.customExceptions.PersonNotFoundException;
import com.example.VaccinationBookingSystem.dto.requestDto.AddPersonRequestDto;
import com.example.VaccinationBookingSystem.dto.responseDto.AddPersonResponseDto;
import com.example.VaccinationBookingSystem.modules.Person;
import com.example.VaccinationBookingSystem.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService; //hello check

    @PostMapping("/add-person")
    public ResponseEntity addPerson(@RequestBody AddPersonRequestDto addPersonRequestDto){
        try{
            AddPersonResponseDto newPerson = personService.addPerson(addPersonRequestDto);
            return new ResponseEntity<>("Congratulation! You have been registered\n" + newPerson, HttpStatus.CREATED);
        }catch(InsufficientInputException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>("Email ID already exist", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-email")
    public ResponseEntity updateEmail(@RequestParam String oldEmail, @RequestParam String newEmail){
        try {
            AddPersonResponseDto updatedPerson = personService.updateEmail(oldEmail, newEmail);
            return new ResponseEntity<>("Email has been Updated\n" + updatedPerson, HttpStatus.ACCEPTED);
        }catch(InsufficientInputException |PersonNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/males-greater-than-a-certain-age/{age}")
    public ResponseEntity malesGreaterThanACertainAge(@PathVariable("age") int age){
        try {
            List<AddPersonResponseDto> personList = personService.malesGreaterThanACertainAge(age);
            return new ResponseEntity<>(personList, HttpStatus.FOUND);
        }catch(PersonNotFoundException e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/females-only-taken-dose1")
    public ResponseEntity femalesOnlyTakenDose1(){
        try {
            List<AddPersonResponseDto> personList = personService.femalesOnlyTakenDose1();
            return new ResponseEntity<>(personList, HttpStatus.FOUND);
        }catch(PersonNotFoundException e){
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fully-vaccinated")
    public ResponseEntity peoplesFullyVaccinated(){
        try {
            List<AddPersonResponseDto> personList = personService.peoplesFullyVaccinated();
            return new ResponseEntity<>(personList, HttpStatus.FOUND);
        }catch(PersonNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/peoples-with-no-dose")
    public ResponseEntity peoplesNotTakeAnyDose(){
        try {
            List<AddPersonResponseDto> personList = personService.peoplesNotTakeAnyDose();
            return new ResponseEntity<>(personList, HttpStatus.FOUND);
        }catch(PersonNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/females-with-only-dose1-greater-than-certain-age/{age}")
    public ResponseEntity femalesWithOnlyDose1GreaterThanCertainAge(@PathVariable("age") int age){
        try {
            List<AddPersonResponseDto> personList = personService.femalesWithOnlyDose1GreaterThanCertainAge(age);
            return new ResponseEntity<>(personList, HttpStatus.FOUND);
        }catch(PersonNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/males-fully-vaccinated-greater-than-certain-age/{age}")
    public ResponseEntity malesFullyVaccinatedGreaterThanCertainAge(@PathVariable("age") int age){
        try {
            List<AddPersonResponseDto> personList = personService.malesFullyVaccinatedGreaterThanCertainAge(age);
            return new ResponseEntity<>(personList, HttpStatus.FOUND);
        }catch(PersonNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
