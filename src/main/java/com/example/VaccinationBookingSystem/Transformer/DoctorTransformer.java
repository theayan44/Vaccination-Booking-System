package com.example.VaccinationBookingSystem.Transformer;

import com.example.VaccinationBookingSystem.dto.requestDto.AddDoctorRequestDto;
import com.example.VaccinationBookingSystem.dto.responseDto.DoctorResponseDto;
import com.example.VaccinationBookingSystem.modules.Doctor;


public class DoctorTransformer {

    public static Doctor doctorRequestDtoToDoctor(AddDoctorRequestDto doctorRequestDto){
        return Doctor.builder()
                .name(doctorRequestDto.getName())
                .age(doctorRequestDto.getAge())
                .gender(doctorRequestDto.getGender())
                .email(doctorRequestDto.getEmail())
                .build();
    }

    public static DoctorResponseDto doctorToDoctorResponseDto(Doctor doctor){
        return DoctorResponseDto.builder()
                .name(doctor.getName())
                .email(doctor.getEmail())
                .age(doctor.getAge())
                .centerName(doctor.getVaccinationCenter().getCenterName())
                .noOfAppointments(doctor.getAppointmentList().size())
                .build();
    }
}
