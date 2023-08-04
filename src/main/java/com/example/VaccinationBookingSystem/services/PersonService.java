package com.example.VaccinationBookingSystem.services;

import com.example.VaccinationBookingSystem.customExceptions.InsufficientInputException;
import com.example.VaccinationBookingSystem.customExceptions.PersonNotFoundException;
import com.example.VaccinationBookingSystem.dto.requestDto.AddPersonRequestDto;
import com.example.VaccinationBookingSystem.dto.responseDto.AddPersonResponseDto;
import com.example.VaccinationBookingSystem.enums.Gender;
import com.example.VaccinationBookingSystem.modules.Person;
import com.example.VaccinationBookingSystem.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;


    public AddPersonResponseDto addPerson(AddPersonRequestDto addPersonRequestDto) {
        // if there are any exception occurs
        if(addPersonRequestDto.getName() == null || addPersonRequestDto.getName().length() == 0 || addPersonRequestDto.getAge() == 0 || addPersonRequestDto.getGender() == null || addPersonRequestDto.getEmail() == null || addPersonRequestDto.getEmail().length() == 0){
            throw new InsufficientInputException("Insufficient data input");
        }

        //addPersonRequestDto -> Person Object
        Person person = new Person();
        person.setName(addPersonRequestDto.getName());
        person.setAge(addPersonRequestDto.getAge());
        person.setGender(addPersonRequestDto.getGender());
        person.setEmail(addPersonRequestDto.getEmail());

        //save the person object in DataBase
        Person savedPerson =  personRepository.save(person);

        //person object -> addPersonResponseDto
        AddPersonResponseDto personResponse = new AddPersonResponseDto();
        personResponse.setName(savedPerson.getName());
        personResponse.setEmail(savedPerson.getEmail());
        personResponse.setAge(savedPerson.getAge());

        return personResponse;
    }

    public AddPersonResponseDto updateEmail(String oldEmail, String newEmail) {
        // check for any exception
        if(oldEmail == null || oldEmail.length() == 0 || newEmail == null || newEmail.length() == 0){
            throw new InsufficientInputException("Insufficient data input");
        }

        // check if Email exist in Database
        Person person = personRepository.findByEmail(oldEmail);
        if(person == null){
            throw new PersonNotFoundException("Email Id doesn't exist");
        }

        // update the email
        person.setEmail(newEmail);

        // now save the updated person in the Database
        Person savedPerson =  personRepository.save(person);

        //person object -> addPersonResponseDto
        AddPersonResponseDto personResponse = new AddPersonResponseDto();
        personResponse.setName(savedPerson.getName());
        personResponse.setEmail(savedPerson.getEmail());
        personResponse.setAge(savedPerson.getAge());

        return personResponse;
    }

    public List<AddPersonResponseDto> malesGreaterThanACertainAge(int age) {
        List<AddPersonResponseDto> ansList = new ArrayList<>();

        List<Person> personList = personRepository.findByGender(Gender.MALE);

        //now check the given age with every person age in the DB
        for(Person currPerson : personList){
            if(currPerson.getAge() > age){
                // prepare ResponseDto Object
                AddPersonResponseDto personResponse = new AddPersonResponseDto();
                personResponse.setName(currPerson.getName());
                personResponse.setEmail(currPerson.getEmail());
                personResponse.setAge(currPerson.getAge());

                // add the response object to the ans list
                ansList.add(personResponse);
            }
        }

        // if there are no person found
        if(ansList.size() == 0){
            throw new PersonNotFoundException("No Such Male Found");
        }

        return ansList;
    }

    public List<AddPersonResponseDto> femalesOnlyTakenDose1() {
        List<AddPersonResponseDto> ansList = new ArrayList<>();

        List<Person> personList = personRepository.findByGender(Gender.FEMALE);

        //now check every female in the DB
        for(Person currPerson : personList){
            if(currPerson.isDose1Taken() && !currPerson.isDose2Taken()){
                // prepare ResponseDto Object
                AddPersonResponseDto personResponse = new AddPersonResponseDto();
                personResponse.setName(currPerson.getName());
                personResponse.setEmail(currPerson.getEmail());
                personResponse.setAge(currPerson.getAge());

                // add the response object to the ans list
                ansList.add(personResponse);
            }
        }

        // if there are no person found
        if(ansList.size() == 0){
            throw new PersonNotFoundException("No Such Female Found");
        }

        return ansList;
    }

    public List<AddPersonResponseDto> peoplesFullyVaccinated() {
        List<AddPersonResponseDto> ansList = new ArrayList<>();

        List<Person> personList = personRepository.findByDose1TakenAndDose2Taken(true, true);

        // if there are no person
        if(personList == null || personList.size() == 0){
            throw new PersonNotFoundException("No Such Person Found");
        }

        // convert to ResponseDto
        for(Person currPerson : personList){
            // prepare the ResponseDto Object
            AddPersonResponseDto personResponse = new AddPersonResponseDto();
            personResponse.setName(currPerson.getName());
            personResponse.setEmail(currPerson.getEmail());
            personResponse.setAge(currPerson.getAge());

            // add the Response object to the ansList
            ansList.add(personResponse);
        }

        return ansList;
    }

    public List<AddPersonResponseDto> peoplesNotTakeAnyDose() {
        List<AddPersonResponseDto> ansList = new ArrayList<>();

        List<Person> personList = personRepository.findByDose1TakenAndDose2Taken(false, false);

        // if there are no person
        if(personList == null || personList.size() == 0){
            throw new PersonNotFoundException("No Such Person Found");
        }

        // convert to ResponseDto
        for(Person currPerson : personList){
            // prepare the ResponseDto Object
            AddPersonResponseDto personResponse = new AddPersonResponseDto();
            personResponse.setName(currPerson.getName());
            personResponse.setEmail(currPerson.getEmail());
            personResponse.setAge(currPerson.getAge());

            // add the Response object to the ansList
            ansList.add(personResponse);
        }

        return ansList;
    }

    public List<AddPersonResponseDto> femalesWithOnlyDose1GreaterThanCertainAge(int age) {
        List<AddPersonResponseDto> personList = femalesOnlyTakenDose1();

        List<AddPersonResponseDto> ansList = new ArrayList<>();

        // now check the age condition fulfill or not for every person
        for(AddPersonResponseDto currPerson : personList){
            if(currPerson.getAge() > age){
                // prepare ResponseDto Object
                AddPersonResponseDto personResponse = new AddPersonResponseDto();
                personResponse.setName(currPerson.getName());
                personResponse.setEmail(currPerson.getEmail());
                personResponse.setAge(currPerson.getAge());

                // add the response object to the ans list
                ansList.add(personResponse);
            }
        }

        // if there are no person found
        if(ansList.size() == 0){
            throw new PersonNotFoundException("No Such Female Found");
        }

        return ansList;
    }

    public List<AddPersonResponseDto> malesFullyVaccinatedGreaterThanCertainAge(int age) {
        List<Person> personList = personRepository.findByDose1TakenAndDose2TakenAndGender(true, true, Gender.MALE);

        // if there are no person found
        if(personList == null || personList.size() == 0){
            throw new PersonNotFoundException("No Such Male Found");
        }

        List<AddPersonResponseDto> ansList = new ArrayList<>();

        // now check the age condition fulfill or not for every person
        for(Person currPerson : personList){
            if(currPerson.getAge() > age){
                // prepare ResponseDto Object
                AddPersonResponseDto personResponse = new AddPersonResponseDto();
                personResponse.setName(currPerson.getName());
                personResponse.setEmail(currPerson.getEmail());
                personResponse.setAge(currPerson.getAge());

                // add the response object to the ans list
                ansList.add(personResponse);
            }
        }

        // if there are no person found
        if(ansList.size() == 0){
            throw new PersonNotFoundException("No Such Male Found");
        }

        return ansList;
    }
}
