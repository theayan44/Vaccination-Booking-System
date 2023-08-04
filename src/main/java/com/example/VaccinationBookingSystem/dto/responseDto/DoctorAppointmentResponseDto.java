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
public class DoctorAppointmentResponseDto {
    String appointmentId;

    Date appointmentDate;

    String personName;

    String personEmail;

    String centerName;
}
