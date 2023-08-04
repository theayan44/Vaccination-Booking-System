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
public class TakeDose1ResponseDto {
    DoseType doseType;

    Date vaccinationDate;

    AddPersonResponseDto personDetails;

    @Override
    public String toString() {
        return "Details: {" +
                "\n\tDoseType: " + doseType + "," +
                "\n\tDate: " + vaccinationDate + "," +
                "\n" + personDetails +
                "\n}";
    }
}
