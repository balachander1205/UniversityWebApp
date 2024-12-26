package com.api.university.service;

import com.api.university.entity.AppointmentsEntity;
import com.api.university.entity.RepresentativeEntity;
import com.api.university.repository.RepresentativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepresentativeServiceImpl implements RepresentativeService{
    @Autowired
    RepresentativeRepository representativeRepository;

    public List<AppointmentsEntity> getRepresentativeAppointmentsByEmailID(String email){
        return representativeRepository.getRepresentativeAppointmentsByEmailID(email);
    }

    @Override
    public List<RepresentativeEntity> getAllRepresentatives() {
        return representativeRepository.getAllRepresentatives();
    }

    public void createRepresentative(String repname, String email, String phonenumber,
                                     String profilepic, String username, String password, String universityID, String availability){
        representativeRepository.createRepresentative(repname, email, phonenumber,
                profilepic, username, password, universityID, availability);
    }

    public RepresentativeEntity getRepresentativeByUsername( String username){
        return representativeRepository.getRepresentativeByUsername(username);
    }

    @Override
    public RepresentativeEntity getRepresentativeByEmail(String email) {
        return representativeRepository.getRepresentativeByEmail(email);
    }

    @Override
    public List<RepresentativeEntity> getRepresentativeByUniversityId(String universityID) {
        return representativeRepository.getRepresentativeByUniversityId(universityID);
    }
}
