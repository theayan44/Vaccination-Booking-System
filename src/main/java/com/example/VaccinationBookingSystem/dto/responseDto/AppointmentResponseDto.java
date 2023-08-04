package com.example.VaccinationBookingSystem.dto.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AppointmentResponseDto {

    String appointmentId;

    Date appointmentDate;

    String personName;

    String personEmail;

    String doctorName;

    String doctorEmail;

    String centerName;
}
