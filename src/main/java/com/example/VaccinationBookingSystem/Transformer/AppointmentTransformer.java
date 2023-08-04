package com.example.VaccinationBookingSystem.Transformer;

import com.example.VaccinationBookingSystem.dto.responseDto.AppointmentResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.DoctorAppointmentResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.DoctorResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.PersonAppointmentResponseDto;
import com.example.VaccinationBookingSystem.modules.Appointment;
import com.example.VaccinationBookingSystem.modules.Doctor;
import com.example.VaccinationBookingSystem.modules.Person;

import java.util.UUID;

public class AppointmentTransformer {

    public static Appointment getAppointmentEntity(Person person, Doctor doctor){
        return Appointment.builder()
                .appointmentId(UUID.randomUUID().toString())
                .person(person)
                .doctor(doctor)
                .build();
    }

    public static AppointmentResponseDto appointmentToAppointmentResponseDto(Appointment appointment){
        return AppointmentResponseDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentDate(appointment.getAppointmentDate())
                .personName(appointment.getPerson().getName())
                .personEmail(appointment.getPerson().getEmail())
                .doctorName(appointment.getDoctor().getName())
                .doctorEmail(appointment.getDoctor().getEmail())
                .centerName(appointment.getDoctor().getVaccinationCenter().getCenterName())
                .build();
    }

    public static PersonAppointmentResponseDto appointmentToPersonAppointmentResponseDto(Appointment appointment){
        return PersonAppointmentResponseDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentDate(appointment.getAppointmentDate())
                .doctorName(appointment.getDoctor().getName())
                .doctorEmail(appointment.getDoctor().getEmail())
                .centerName(appointment.getDoctor().getVaccinationCenter().getCenterName())
                .build();
    }

    public static DoctorAppointmentResponseDto appointmentToDoctorAppointmentResponseDto(Appointment appointment){
        return DoctorAppointmentResponseDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentDate(appointment.getAppointmentDate())
                .personName(appointment.getPerson().getName())
                .personEmail(appointment.getPerson().getEmail())
                .centerName(appointment.getDoctor().getVaccinationCenter().getCenterName())
                .build();
    }
}
