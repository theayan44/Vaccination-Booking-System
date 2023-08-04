package com.example.VaccinationBookingSystem.customExceptions;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String message){
        super(message);
    }
}
