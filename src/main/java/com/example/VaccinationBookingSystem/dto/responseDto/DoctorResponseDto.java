package com.example.VaccinationBookingSystem.dto.responseDto;

import com.example.VaccinationBookingSystem.modules.VaccinationCenter;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DoctorResponseDto {

    String name;

    String email;

    int age;

    String centerName;

    int noOfAppointments;
}
