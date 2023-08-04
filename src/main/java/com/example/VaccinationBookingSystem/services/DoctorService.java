package com.example.VaccinationBookingSystem.services;

import com.example.VaccinationBookingSystem.Transformer.DoctorTransformer;
import com.example.VaccinationBookingSystem.Transformer.VaccinationCenterTransformer;
import com.example.VaccinationBookingSystem.customExceptions.*;
import com.example.VaccinationBookingSystem.dto.requestDto.AddDoctorRequestDto;
import com.example.VaccinationBookingSystem.dto.requestDto.DoctorsEmailOrAgeUpdateRequestDto;
import com.example.VaccinationBookingSystem.dto.responseDto.DoctorResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.NoOFDoctorsInACenterResponseDto;
import com.example.VaccinationBookingSystem.modules.Doctor;
import com.example.VaccinationBookingSystem.modules.VaccinationCenter;
import com.example.VaccinationBookingSystem.repositories.DoctorRepository;
import com.example.VaccinationBookingSystem.repositories.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;

    public DoctorResponseDto addDoctor(AddDoctorRequestDto doctorRequestDto) {
        // check if vaccination center not exists
        if(vaccinationCenterRepository.findByCenterName(doctorRequestDto.getVaccinationCenterName()) == null){
            throw new VaccinationCenterNotFoundException("Vaccination Center Doesn't Exists");
        }

        // check if email id already exists
        if(doctorRepository.findByEmail(doctorRequestDto.getEmail()) != null){
            throw new EmailIdAlreadyExistsException("Email Id Already Exists");
        }


        // DoctorRequestDto -> Doctor Entity
        Doctor doctor = DoctorTransformer.doctorRequestDtoToDoctor(doctorRequestDto);
        doctor.setAppointmentList(new ArrayList<>());
        doctor.setVaccinationCenter(vaccinationCenterRepository.findByCenterName(doctorRequestDto.getVaccinationCenterName()));


        // save the doctor entity to the DB
        Doctor savedDoctor = doctorRepository.save(doctor);

        // now update in the Vaccination Center Entity and in the DB
        VaccinationCenter vaccinationCenter = savedDoctor.getVaccinationCenter();
        List<Doctor> doctorList = vaccinationCenter.getDoctorList();
        doctorList.add(savedDoctor);
        vaccinationCenter.setDoctorList(doctorList);
        vaccinationCenterRepository.save(vaccinationCenter);

        // Doctor Entity -> DoctorResponseDto and return
        return DoctorTransformer.doctorToDoctorResponseDto(savedDoctor);
    }


    public List<DoctorResponseDto> doctorWithHighestAppointments() {
        // get all the doctors from DB
        List<Doctor> doctorList = doctorRepository.findAll();

        // now loop around and find the max appointments
        int maxAppointments = 0;
        for(Doctor currDoctor : doctorList){
            maxAppointments = Math.max(maxAppointments, currDoctor.getAppointmentList().size());
        }

        // if no appointment found
        if(maxAppointments == 0){
            throw new DoctorNotFoundException("No Doctor Found, No Appointments Booked Yet");
        }

        // now loop around and store the doctors name having max appointments
        List<DoctorResponseDto> doctorsListWithMaxAppointments = new ArrayList<>();
        for(Doctor currDoctor : doctorList){
            if(currDoctor.getAppointmentList().size() == maxAppointments){
                doctorsListWithMaxAppointments.add(DoctorTransformer.doctorToDoctorResponseDto(currDoctor));
            }
        }

        return doctorsListWithMaxAppointments;
    }


    public List<DoctorResponseDto> doctorsGreaterThanCertainAge(int age) {
        // get the doctors list whose age is greater than the given age
        List<Doctor> doctorList = doctorRepository.ageGreaterThanX(age);

        // now loop around and store the doctors
        List<DoctorResponseDto> ansList = new ArrayList<>();
        for(Doctor currDoctor : doctorList){
            ansList.add(DoctorTransformer.doctorToDoctorResponseDto(currDoctor));
        }

        return ansList;
    }


    public DoctorResponseDto updateEmailOrAge(DoctorsEmailOrAgeUpdateRequestDto doctorsEmailOrAgeUpdateRequestDto) {
        // if old email id is not given
        if(doctorsEmailOrAgeUpdateRequestDto.getOldEmail() == null || doctorsEmailOrAgeUpdateRequestDto.getOldEmail().length() == 0){
            throw new InsufficientInputException("Old Email Id is Required ");
        }

        // check if the doctor entity is exists in the DB
        Doctor doctor = doctorRepository.findByEmail(doctorsEmailOrAgeUpdateRequestDto.getOldEmail());
        if(doctor == null){
            throw new DoctorNotFoundException("Doctor with This Email Doesn't Exists");
        }

        // Updating Email Id
        String newEmail = doctorsEmailOrAgeUpdateRequestDto.getNewEmail();
        if(newEmail != null && newEmail.length() > 0){
            // if the new email is already used by someone else
            if(doctorRepository.findByEmail(newEmail) != null){
                throw new EmailIdAlreadyExistsException("The New Email Id is Already Used by Someone Else");
            }

            // else update the email id
            doctor.setEmail(newEmail);
        }

        // Updating Age
        int age = doctorsEmailOrAgeUpdateRequestDto.getAge();
        if(age != 0){
            doctor.setAge(age);
        }

        // save to the DB
        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorTransformer.doctorToDoctorResponseDto(savedDoctor);
    }
}
