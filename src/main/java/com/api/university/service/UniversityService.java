package com.api.university.service;

import com.api.university.entity.UniversityEntity;

import java.util.List;

public interface UniversityService {
    public List<UniversityEntity> getAllUniversities();
    public void insertUniversity(String universityname,String description, String location,
                                 String repname, String position, String admissionintake,
                                 String username, String password, String state, String images,
                                 String coursetype, String isrecommended, String universityid,String country);
    public void updateUniversity(String universityname,String description, String location,
                                 String repname, String position, String admissionintake,
                                 String username, String password, String state, String images,
                                 String coursetype, String isrecommended, String universityid,String country);
}
