package com.example.VaccinationBookingSystem.dto.responseDto;

import com.example.VaccinationBookingSystem.enums.CenterType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VaccinationCenterResponseDto {

    String centerName;

    String address;

    CenterType centerType;
}
