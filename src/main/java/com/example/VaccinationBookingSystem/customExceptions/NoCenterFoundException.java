package com.example.VaccinationBookingSystem.customExceptions;

public class NoCenterFoundException extends RuntimeException{
    public NoCenterFoundException(String message){
        super(message);
    }
}
