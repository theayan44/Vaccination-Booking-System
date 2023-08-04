package com.example.VaccinationBookingSystem.services;

import com.example.VaccinationBookingSystem.Transformer.CertificateTransformer;
import com.example.VaccinationBookingSystem.customExceptions.Dose1NotTakenException;
import com.example.VaccinationBookingSystem.customExceptions.Dose2NotTakenException;
import com.example.VaccinationBookingSystem.customExceptions.PersonNotFoundException;
import com.example.VaccinationBookingSystem.dto.responseDto.CertificateResponseDto;
import com.example.VaccinationBookingSystem.modules.Certificate;
import com.example.VaccinationBookingSystem.modules.Person;
import com.example.VaccinationBookingSystem.repositories.CertificateRepository;
import com.example.VaccinationBookingSystem.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CertificateService {

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    PersonRepository personRepository;


    public Certificate generateCertificate(Person person){
        // generate the certificate
        Certificate certificate = CertificateTransformer.generateCertificate();
        certificate.setPerson(person);

        // save certificate in DB and return
        return certificateRepository.save(certificate);
    }


    public CertificateResponseDto getDose1Certificate(String personEmail) {
        // check email exists or not
        Person person = personRepository.findByEmail(personEmail);
        if(person == null){
            throw new PersonNotFoundException("Email Id Doesn't Exists");
        }

        // check dose 1 taken or not
        if(!person.isDose1Taken()){
            throw new Dose1NotTakenException("Dose 1 Not Taken Yet");
        }

        // get the certificate
        Certificate certificate = person.getCertificateList().get(0);

        // prepare responseDto and return
        return CertificateTransformer.getDose1CertificateResponse(certificate);
    }


    public CertificateResponseDto getDose2Certificate(String personEmail) {
        // check email exists or not
        Person person = personRepository.findByEmail(personEmail);
        if(person == null){
            throw new PersonNotFoundException("Email Id Doesn't Exists");
        }

        // check dose 2 taken or not
        if(!person.isDose2Taken()){
            throw new Dose2NotTakenException("Dose 2 Not Taken Yet");
        }

        // get the certificate
        Certificate certificate = person.getCertificateList().get(1);

        // prepare responseDto and return
        return CertificateTransformer.getDose2CertificateResponse(certificate);
    }
}
