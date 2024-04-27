package com.api.university.service;

import java.sql.Timestamp;

public interface StudentService {
    public void insertStudent(String universityname,String studentname, String location,
                                 String studentlocation, String phonenumber,String email, String feedback,
                                 Timestamp createdatetime);
}
