package com.api.university.repository;

import com.api.university.entity.StudentPreferencesEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface StudentPreferenceRepository extends CrudRepository<StudentPreferencesEntity, Long> {

    @Query(value = "select * from std_preferences", nativeQuery = true)
    List<StudentPreferencesEntity> getAllPreferences();
}
