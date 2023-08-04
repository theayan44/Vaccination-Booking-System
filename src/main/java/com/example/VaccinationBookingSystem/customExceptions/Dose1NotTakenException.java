package com.example.VaccinationBookingSystem.customExceptions;

public class Dose1NotTakenException extends RuntimeException{
    public Dose1NotTakenException(String message){
        super(message);
    }
}
