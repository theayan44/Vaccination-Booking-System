package com.example.VaccinationBookingSystem.customExceptions;

public class InsufficientInputException extends RuntimeException{
    public InsufficientInputException(String message){
        super(message);
    }
}
