package com.example.VaccinationBookingSystem.dto.requestDto;

import com.example.VaccinationBookingSystem.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPersonRequestDto {
    String name;

    int age;

    Gender gender;

    String email;
}
