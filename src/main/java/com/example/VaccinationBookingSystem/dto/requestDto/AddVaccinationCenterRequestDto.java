package com.example.VaccinationBookingSystem.dto.requestDto;

import com.example.VaccinationBookingSystem.enums.CenterType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddVaccinationCenterRequestDto {

    String centerName;

    CenterType centerType;

    String address;
}
