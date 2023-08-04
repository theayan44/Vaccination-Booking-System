package com.example.VaccinationBookingSystem.dto.responseDto;

import com.example.VaccinationBookingSystem.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddPersonResponseDto {

    String name;

    String email;

    int age;

    Gender gender;

    @Override
    public String toString() {
        return "Person Details: {" +
                "\n\tName: " + name + "," +
                "\n\tEmail: " + email +
                "\n\tAge: " + age +
                "\n\tGender: " + gender +
                "\n}";
    }
}
