package com.example.VaccinationBookingSystem.dto.requestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorsEmailOrAgeUpdateRequestDto {
    String oldEmail;

    String newEmail;

    int age;
}
