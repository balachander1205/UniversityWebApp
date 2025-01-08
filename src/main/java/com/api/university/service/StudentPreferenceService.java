package com.api.university.service;


import com.api.university.entity.StudentPreferencesEntity;

import java.util.List;

public interface StudentPreferenceService {
    List<StudentPreferencesEntity> getAllPreferences();
}
