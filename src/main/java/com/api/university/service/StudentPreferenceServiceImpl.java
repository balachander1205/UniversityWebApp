package com.api.university.service;

import com.api.university.entity.StudentPreferencesEntity;
import com.api.university.repository.StudentPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentPreferenceServiceImpl implements StudentPreferenceService {

    @Autowired
    StudentPreferenceRepository studentPreferenceRepository;

    @Override
    public List<StudentPreferencesEntity> getAllPreferences() {
        return studentPreferenceRepository.getAllPreferences();
    }
}
