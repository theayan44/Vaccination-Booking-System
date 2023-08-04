package com.example.VaccinationBookingSystem.customExceptions;

public class VaccinationCenterAlreadyExistException extends RuntimeException{
    public VaccinationCenterAlreadyExistException(String message){
        super(message);
    }
}
