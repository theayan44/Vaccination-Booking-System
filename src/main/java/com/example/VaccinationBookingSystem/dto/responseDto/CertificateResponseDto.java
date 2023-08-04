package com.example.VaccinationBookingSystem.dto.responseDto;

import com.example.VaccinationBookingSystem.enums.DoseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CertificateResponseDto {

    AddPersonResponseDto personDetails;

    String certificateNo;

    DoseType vaccinationName;

    Date vaccinationDate;
}
