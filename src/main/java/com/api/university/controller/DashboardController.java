package com.api.university.controller;

import com.api.university.entity.AppointmentsEntity;
import com.api.university.entity.StudentEntity;
import com.api.university.model.AnalyticsResponse;
import com.api.university.repository.AppointmentsRepository;
import com.api.university.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/api")
@Slf4j
public class DashboardController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @GetMapping("/dashboardDetails")
    public String dashboardDetails(HttpSession httpSession, ModelMap modelMap, RedirectAttributes redir) {
        return "dashboard";
    }

    @PostMapping(value = "/analytics", produces = "application/json")
    @ResponseBody
    public ResponseEntity<AnalyticsResponse> getAnalytics(){
        List<StudentEntity> totalStudents = studentRepository.getTotalStudents();
        List<StudentEntity> activeStudents = studentRepository.getActiveStudents();
        List<AppointmentsEntity> totalAppointments = appointmentsRepository.getAllAppointments();

        AnalyticsResponse analyticsResponse = new AnalyticsResponse();
        analyticsResponse.setActiveStudents((activeStudents.size()));
        analyticsResponse.setTotalAppointments((totalAppointments.size()));
        analyticsResponse.setTotalStudents((totalStudents.size()));
        log.info("JsonObject={}",analyticsResponse);

        ResponseEntity<AnalyticsResponse> entity = new ResponseEntity<>(analyticsResponse, HttpStatus.ACCEPTED);
        return entity;
    }
}
