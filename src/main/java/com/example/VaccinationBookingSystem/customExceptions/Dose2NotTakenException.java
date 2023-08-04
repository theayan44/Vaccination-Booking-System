package com.example.VaccinationBookingSystem.customExceptions;

public class Dose2NotTakenException extends RuntimeException{
    public Dose2NotTakenException(String message){
        super(message);
    }
}
