package com.example.VaccinationBookingSystem.dto.responseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CenterWithAllDetailsResponseDto {
    VaccinationCenterResponseDto vaccinationCenterResponseDto;

    int noOfDoctors;

    List<String> doctorsName = new ArrayList<>();
}
