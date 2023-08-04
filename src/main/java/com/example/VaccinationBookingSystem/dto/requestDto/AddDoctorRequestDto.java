package com.example.VaccinationBookingSystem.dto.requestDto;

import com.example.VaccinationBookingSystem.enums.Gender;
import com.example.VaccinationBookingSystem.modules.VaccinationCenter;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddDoctorRequestDto {
    String name;

    int age;

    Gender gender;

    String email;

    String vaccinationCenterName;
}
