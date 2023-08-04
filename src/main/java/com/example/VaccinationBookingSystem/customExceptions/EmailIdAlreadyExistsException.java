package com.example.VaccinationBookingSystem.customExceptions;

public class EmailIdAlreadyExistsException extends RuntimeException{
    public EmailIdAlreadyExistsException(String message){
        super(message);
    }
}
