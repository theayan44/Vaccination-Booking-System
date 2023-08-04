package com.example.VaccinationBookingSystem.Transformer;

import com.example.VaccinationBookingSystem.dto.responseDto.AddPersonResponseDto;
import com.example.VaccinationBookingSystem.modules.Person;

public class PersonTransformer {
    public static AddPersonResponseDto personToPersonResponse(Person person){
        return AddPersonResponseDto.builder()
                .name(person.getName())
                .email(person.getEmail())
                .age(person.getAge())
                .gender(person.getGender())
                .build();
    }
}
