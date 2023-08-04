package com.example.VaccinationBookingSystem.Transformer;

import com.example.VaccinationBookingSystem.dto.responseDto.AddPersonResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.CertificateResponseDto;
import com.example.VaccinationBookingSystem.enums.DoseType;
import com.example.VaccinationBookingSystem.modules.Certificate;
import com.example.VaccinationBookingSystem.modules.Person;

import java.util.Date;
import java.util.UUID;

public class CertificateTransformer {
    public static Certificate generateCertificate(){
        return Certificate.builder()
                .certificateNo(UUID.randomUUID().toString())
                .build();
    }

    public static CertificateResponseDto getDose1CertificateResponse(Certificate certificate){
        return CertificateResponseDto.builder()
                .personDetails(PersonTransformer.personToPersonResponse(certificate.getPerson()))
                .certificateNo(certificate.getCertificateNo())
                .vaccinationName(certificate.getPerson().getDoseList().get(0).getDoseType())
                .vaccinationDate(certificate.getPerson().getDoseList().get(0).getVaccinationDate())
                .build();
    }

    public static CertificateResponseDto getDose2CertificateResponse(Certificate certificate){
        return CertificateResponseDto.builder()
                .personDetails(PersonTransformer.personToPersonResponse(certificate.getPerson()))
                .certificateNo(certificate.getCertificateNo())
                .vaccinationName(certificate.getPerson().getDoseList().get(1).getDoseType())
                .vaccinationDate(certificate.getPerson().getDoseList().get(1).getVaccinationDate())
                .build();
    }
}
