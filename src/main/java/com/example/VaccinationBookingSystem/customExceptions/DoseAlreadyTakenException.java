package com.example.VaccinationBookingSystem.customExceptions;

public class DoseAlreadyTakenException extends RuntimeException{
    public DoseAlreadyTakenException(String message){
        super(message);
    }
}
