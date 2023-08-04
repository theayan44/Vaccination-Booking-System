package com.example.VaccinationBookingSystem.services;

import com.example.VaccinationBookingSystem.customExceptions.Dose1NotTakenException;
import com.example.VaccinationBookingSystem.customExceptions.DoseAlreadyTakenException;
import com.example.VaccinationBookingSystem.customExceptions.InsufficientInputException;
import com.example.VaccinationBookingSystem.customExceptions.PersonNotFoundException;
import com.example.VaccinationBookingSystem.dto.responseDto.AddPersonResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.TakeDose1ResponseDto;
import com.example.VaccinationBookingSystem.enums.DoseType;
import com.example.VaccinationBookingSystem.modules.Certificate;
import com.example.VaccinationBookingSystem.modules.Dose;
import com.example.VaccinationBookingSystem.modules.Person;
import com.example.VaccinationBookingSystem.repositories.DoseRepository;
import com.example.VaccinationBookingSystem.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoseService {
    @Autowired
    DoseRepository doseRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    CertificateService certificateService;
    @Autowired
    JavaMailSender javaMailSender;


    public TakeDose1ResponseDto takeDose1(int personId, DoseType doseType) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        //If the Person doesn't exist in the Database
        if(optionalPerson.isEmpty()){
            throw new PersonNotFoundException("Person Id Not Found");
        }

        //if Dose 1 is already taken
        if(optionalPerson.get().isDose1Taken()){
            throw new DoseAlreadyTakenException("Dose 1 is already taken");
        }

        //set all the parameter of Dose Entity
        String doseId = String.valueOf(UUID.randomUUID());
        Dose newDose = new Dose();
        newDose.setDoseID(doseId);
        newDose.setDoseType(doseType);
        newDose.setPerson(optionalPerson.get());
        Dose doseTaken = doseRepository.save(newDose);

        //update all the parameter of Person Entity after dose1 taken
        Person person = optionalPerson.get();
        person.setDose1Taken(true);
        List<Dose> doseList= new ArrayList<>();
        doseList.add(doseTaken);
        person.setDoseList(doseList);
        person.getCertificateList().add(certificateService.generateCertificate(person));
        personRepository.save(person);

        // Prepare the personResponseObject
        AddPersonResponseDto personResponse = new AddPersonResponseDto();
        personResponse.setName(person.getName());
        personResponse.setEmail(person.getEmail());
        personResponse.setAge(person.getAge());

        // generate the mail and send to person
        String message = "Congratulation " + person.getName().split(" ")[0].toUpperCase() + " !!! You have completed your first dose."
                + "\nDetails Below :"
                + "\n\tName: " + person.getName()
                + "\n\tEmail: " + person.getEmail()
                + "\n\tAge: " + person.getAge()
                + "\n\tGender: " + person.getGender()
                + "\n\tVaccination Name: " + doseTaken.getDoseType()
                + "\n\tVaccination Date: " + doseTaken.getVaccinationDate();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("ayan.testemail@gmail.com");
        simpleMailMessage.setTo(person.getEmail());
        simpleMailMessage.setSubject("Dose 1 Confirmation");
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);

        //Dose Object -> TakeDose1ResponseDto
        TakeDose1ResponseDto dose1Response = new TakeDose1ResponseDto();
        dose1Response.setDoseType(doseTaken.getDoseType());
        dose1Response.setVaccinationDate(doseTaken.getVaccinationDate());
        dose1Response.setPersonDetails(personResponse);

        // return the responseDto
        return dose1Response;
    }

    public TakeDose1ResponseDto takeDose2(int personId, DoseType doseType) {
        Optional<Person> optionalPerson = personRepository.findById(personId);

        //If the Person doesn't exist in the Database
        if(optionalPerson.isEmpty()){
            throw new PersonNotFoundException("Person Id Not Found");
        }

        //if Dose 1 is not taken
        if(!optionalPerson.get().isDose1Taken()){
            throw new Dose1NotTakenException("Dose 1 Not Completed");
        }

        //if Dose 2 is already taken
        if(optionalPerson.get().isDose2Taken()){
            throw new DoseAlreadyTakenException("Dose 2 already taken");
        }

        //prepare the Dose entity
        String doseId = String.valueOf(UUID.randomUUID());
        Dose newDose = new Dose();
        newDose.setDoseID(doseId);
        newDose.setDoseType(doseType);
        newDose.setPerson(optionalPerson.get());
        Dose doseTaken = doseRepository.save(newDose);

        //update all the parameter of Person Entity after dose1 taken
        Person person = optionalPerson.get();
        person.setDose2Taken(true);
        List<Dose> doseList= person.getDoseList();
        doseList.add(doseTaken);
        person.getCertificateList().add(certificateService.generateCertificate(person));
        person.setDoseList(doseList);
        personRepository.save(person);

        // Prepare the personResponseObject
        AddPersonResponseDto personResponse = new AddPersonResponseDto();
        personResponse.setName(person.getName());
        personResponse.setEmail(person.getEmail());
        personResponse.setAge(person.getAge());

        // generate the mail and send to person
        String message = "Congratulation " + person.getName().split(" ")[0].toUpperCase() + " !!! You have completed your second dose."
                + "\nDetails Below :"
                + "\n\tName: " + person.getName()
                + "\n\tEmail: " + person.getEmail()
                + "\n\tAge: " + person.getAge()
                + "\n\tGender: " + person.getGender()
                + "\n\tVaccination Name: " + doseTaken.getDoseType()
                + "\n\tVaccination Date: " + doseTaken.getVaccinationDate();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("ayan.testemail@gmail.com");
        simpleMailMessage.setTo(person.getEmail());
        simpleMailMessage.setSubject("Dose 2 Confirmation");
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);

        //Dose Object -> TakeDose1ResponseDto
        TakeDose1ResponseDto dose2Response = new TakeDose1ResponseDto();
        dose2Response.setDoseType(doseTaken.getDoseType());
        dose2Response.setVaccinationDate(doseTaken.getVaccinationDate());
        dose2Response.setPersonDetails(personResponse);

        // return the responseDto
        return dose2Response;
    }
}
