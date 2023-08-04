package com.example.VaccinationBookingSystem.Transformer;

import com.example.VaccinationBookingSystem.dto.requestDto.AddVaccinationCenterRequestDto;
import com.example.VaccinationBookingSystem.dto.responseDto.NoOFDoctorsInACenterResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.VaccinationCenterResponseDto;
import com.example.VaccinationBookingSystem.modules.VaccinationCenter;

public class VaccinationCenterTransformer {

    public static VaccinationCenter vaccinationCenterRequestDtoToVaccinationCenter(AddVaccinationCenterRequestDto vaccinationCenterRequestDto){
        return VaccinationCenter.builder()
                .centerName(vaccinationCenterRequestDto.getCenterName())
                .address(vaccinationCenterRequestDto.getAddress())
                .centerType(vaccinationCenterRequestDto.getCenterType())
                .build();
    }

    public static VaccinationCenterResponseDto vaccinationCenterToVaccinationCenterResponseDto(VaccinationCenter vaccinationCenter){
        return VaccinationCenterResponseDto.builder()
                .centerName(vaccinationCenter.getCenterName())
                .address(vaccinationCenter.getAddress())
                .centerType(vaccinationCenter.getCenterType())
                .build();
    }

    public static NoOFDoctorsInACenterResponseDto findAllDoctorsNameInACenter(VaccinationCenter vaccinationCenter){
        return NoOFDoctorsInACenterResponseDto.builder()
                .centerName(vaccinationCenter.getCenterName())
                .noOfDoctors(vaccinationCenter.getDoctorList().size())
                .build();
    }
}
