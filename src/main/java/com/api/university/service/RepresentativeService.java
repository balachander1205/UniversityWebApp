package com.api.university.service;

import com.api.university.entity.AppointmentsEntity;
import com.api.university.entity.RepresentativeEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepresentativeService {
    public List<AppointmentsEntity> getRepresentativeAppointmentsByEmailID(String email);
    public List<RepresentativeEntity> getAllRepresentatives();
    public void createRepresentative(String repname, String email, String phonenumber,
                                     String profilepic, String username, String password, String universityID, String availability);
    public RepresentativeEntity getRepresentativeByUsername( String username);
    public RepresentativeEntity getRepresentativeByEmail(String email);
    public List<RepresentativeEntity> getRepresentativeByUniversityId(String universityID);
    public void updateRepresentative(String repname, String email, String phonenumber,
                                     String username, String password, String universityid, String availability, int id);
}
