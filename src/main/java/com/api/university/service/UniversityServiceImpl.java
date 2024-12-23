package com.api.university.service;

import com.api.university.entity.UniversityEntity;
import com.api.university.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    UniversityRepository universityRepository;

    @Override
    public List<UniversityEntity> getAllUniversities() {
        return universityRepository.getAllUniversities();
    }

    public void insertUniversity(String universityname, String description, String location,
                                 String repname, String position, String admissionintake,
                                 String username, String password, String state, String images,
                                 String coursetype, String isrecommended, String universityid) {
        universityRepository.insertUniversity(universityname, description, location,
                repname, position, admissionintake, username, password, state, images,
                coursetype, isrecommended, universityid);
    }
}
