package com.api.university.service;

import com.api.university.entity.StudentEntity;
import com.api.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public void insertStudent(String universityname, String studentname, String location,
                              String studentlocation, String phonenumber, String email, String feedback, Timestamp createdatetime) {
        studentRepository.insertStudent(universityname, studentname, location,
                studentlocation, phonenumber, email, feedback, createdatetime);
    }

    public List<StudentEntity> getAllStudents(){
        return studentRepository.getAllStudents();
    }

    public List<StudentEntity> getTotalStudents(){
        return studentRepository.getTotalStudents();
    }
    public List<StudentEntity> getActiveStudents(){
        return studentRepository.getActiveStudents();
    }
}
