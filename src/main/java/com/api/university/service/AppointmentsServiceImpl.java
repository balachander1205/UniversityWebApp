package com.api.university.service;

import com.api.university.entity.AppointmentsEntity;
import com.api.university.repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AppointmentsServiceImpl implements AppointmentsService {

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Override
    public void createAppointment(String studentname, String repname, String universityname, String location, String appointmentdate, String appointmentslot, Timestamp createdatetime) {
        appointmentsRepository.createAppointment(studentname, repname, universityname, location, appointmentdate, appointmentslot, createdatetime);
    }

    @Override
    public List<AppointmentsEntity> getAllAppointments() {
        return appointmentsRepository.getAllAppointments();
    }

    @Override
    public List<AppointmentsEntity> getUpcomingAppointments() {
        return appointmentsRepository.getUpcomingAppointments();
    }

    @Override
    public List<AppointmentsEntity> getAppointmentsByRepname(String repname) {
        return appointmentsRepository.getAppointmentsByRepname(repname);
    }

    public List<Object> groupAppointmentsByDate(){
        return appointmentsRepository.groupAppointmentsByDate();
    }

    @Override
    public List<Object[]> countAppointmentsByDate() {
        return appointmentsRepository.countAppointmentsByDate();
    }

    @Override
    public List<Object[]> countAppointmentsByUniversityDate() {
        return appointmentsRepository.countAppointmentsByUniversityDate();
    }
}
