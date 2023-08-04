package com.example.VaccinationBookingSystem.services;

import com.example.VaccinationBookingSystem.Transformer.VaccinationCenterTransformer;
import com.example.VaccinationBookingSystem.customExceptions.NoCenterFoundException;
import com.example.VaccinationBookingSystem.customExceptions.VaccinationCenterAlreadyExistException;
import com.example.VaccinationBookingSystem.customExceptions.VaccinationCenterNotFoundException;
import com.example.VaccinationBookingSystem.dto.requestDto.AddVaccinationCenterRequestDto;
import com.example.VaccinationBookingSystem.dto.responseDto.NoOFDoctorsInACenterResponseDto;
import com.example.VaccinationBookingSystem.dto.responseDto.VaccinationCenterResponseDto;
import com.example.VaccinationBookingSystem.enums.CenterType;
import com.example.VaccinationBookingSystem.modules.Doctor;
import com.example.VaccinationBookingSystem.modules.VaccinationCenter;
import com.example.VaccinationBookingSystem.repositories.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationCenterService {

    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;


    public VaccinationCenterResponseDto addVaccinationCenter(AddVaccinationCenterRequestDto vaccinationCenterRequestDto) {
        // check if same canter name is already exist
        if(vaccinationCenterRepository.findByCenterName(vaccinationCenterRequestDto.getCenterName()) != null){
            throw new VaccinationCenterAlreadyExistException("Vaccination Center with same name is already exist");
        }

//         vaccinationCenterRequestDto -> vaccinationCenter entity
        VaccinationCenter vaccinationCenter = VaccinationCenterTransformer.vaccinationCenterRequestDtoToVaccinationCenter(vaccinationCenterRequestDto);

        // save the entity in DB
        VaccinationCenter savedVaccinationCenter = vaccinationCenterRepository.save(vaccinationCenter);

        // vaccinationCenter entity -> vaccinationCenterResponseDto and return
        return VaccinationCenterTransformer.vaccinationCenterToVaccinationCenterResponseDto(savedVaccinationCenter);
    }


    public List<String> allDoctorsOfAParticularCenter(String centerName) {
        // get the center from the DB
        VaccinationCenter vaccinationCenter = vaccinationCenterRepository.findByCenterName(centerName);

        // check if no center exists with that name
        if(vaccinationCenter == null){
            throw new VaccinationCenterNotFoundException("Vaccination Center Not Found");
        }

        List<String> listOfDoctorsName = new ArrayList<>();
        List<Doctor> doctorList = vaccinationCenter.getDoctorList();
        for(Doctor currDoctor : doctorList){
            listOfDoctorsName.add(currDoctor.getName());
        }

        return listOfDoctorsName;

    }


    public List<String> allDoctorsOfParticularCenterType(CenterType centerType) {
        // get the all center from DB of same center type
        List<VaccinationCenter> vaccinationCenterList = vaccinationCenterRepository.findByCenterType(centerType);

        // now loop around and add doctor's name one by one
        List<String> ansList = new ArrayList<>();
        for(VaccinationCenter currCenter : vaccinationCenterList){
            List<Doctor> doctorList = currCenter.getDoctorList();
            for(Doctor currDoctor : doctorList){
                ansList.add(currDoctor.getName());
            }
        }
        return ansList;
    }


    public List<NoOFDoctorsInACenterResponseDto> vaccinationCenterWithHighestDoctors() {
        // get all the center from DB
        List<VaccinationCenter> centerList = vaccinationCenterRepository.findAll();

        // now loop around and find the max doctors numbers
        int maxDoctors = 0;
        for(VaccinationCenter currCenter : centerList){
            maxDoctors = Math.max(maxDoctors, currCenter.getDoctorList().size());
        }

        // if no center found
        if(maxDoctors == 0){
            throw new NoCenterFoundException("No Center Found, Doctors Not Added Yet");
        }

        // now get all the center with max number of doctors and prepare DoctorsOfACenterResponseDto
        List<NoOFDoctorsInACenterResponseDto> doctorsOfACenter = new ArrayList<>();
        for(VaccinationCenter currCenter : centerList){
            if(currCenter.getDoctorList().size() == maxDoctors){
                doctorsOfACenter.add(VaccinationCenterTransformer.findAllDoctorsNameInACenter(currCenter));
            }
        }

        return doctorsOfACenter;
    }


    public List<NoOFDoctorsInACenterResponseDto> vaccinationCenterOfParticularTypeWithHighestDoctors(CenterType centerType) {
        // get all the center from DB
        List<VaccinationCenter> centerList = vaccinationCenterRepository.findByCenterType(centerType);

        // now loop around and find the max doctors numbers
        int maxDoctors = 0;
        for(VaccinationCenter currCenter : centerList){
            maxDoctors = Math.max(maxDoctors, currCenter.getDoctorList().size());
        }

        // if no center found
        if(maxDoctors == 0){
            throw new NoCenterFoundException("No Doctors Found in " + centerType.toString());
        }

        // now get all the center with max number of doctors and prepare DoctorsOfACenterResponseDto
        List<NoOFDoctorsInACenterResponseDto> doctorsOfACenter = new ArrayList<>();
        for(VaccinationCenter currCenter : centerList){
            if(currCenter.getDoctorList().size() == maxDoctors){
                doctorsOfACenter.add(VaccinationCenterTransformer.findAllDoctorsNameInACenter(currCenter));
            }
        }

        return doctorsOfACenter;
    }

}
