package com.example.VaccinationBookingSystem.customExceptions;

public class VaccinationCenterNotFoundException extends RuntimeException{
    public VaccinationCenterNotFoundException(String message){
        super(message);
    }
}
