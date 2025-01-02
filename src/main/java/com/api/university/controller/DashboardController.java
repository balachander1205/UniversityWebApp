package com.api.university.controller;

import com.api.university.entity.AppointmentsEntity;
import com.api.university.entity.RepresentativeEntity;
import com.api.university.entity.StudentEntity;
import com.api.university.entity.UniversityEntity;
import com.api.university.model.AnalyticsResponse;
import com.api.university.repository.AppointmentsRepository;
import com.api.university.repository.StudentRepository;
import com.api.university.repository.UniversityRepository;
import com.api.university.service.RepresentativeService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@Slf4j
public class DashboardController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    RepresentativeService representativeService;

    @GetMapping("/dashboardDetails")
    public String dashboardDetails(HttpSession httpSession, Model model, RedirectAttributes redir) {
        log.info("dashboard:userId={}",httpSession.getAttribute("userId"));
        String userId = httpSession.getAttribute("userId").toString();
        model.addAttribute("userId",userId);
        return "dashboard";
    }

    @PostMapping(value = "/analytics", produces = "application/json")
    @ResponseBody
    public ResponseEntity<AnalyticsResponse> getAnalytics(){
        List<StudentEntity> totalStudents = studentRepository.getTotalStudents();
        List<StudentEntity> activeStudents = studentRepository.getActiveStudents();
        List<AppointmentsEntity> totalAppointments = appointmentsRepository.getAllAppointments();
        List<UniversityEntity> totalUniversities = universityRepository.getAllUniversities();
        List<Object[]> totalAppointmentsByDate = appointmentsRepository.countAppointmentsByDate();
        List<RepresentativeEntity> totalRepresentatives = representativeService.getAllRepresentatives();
        List<Object[]> countAppointmentsByUniversityDate = appointmentsRepository.countAppointmentsByUniversityDate();

        AnalyticsResponse analyticsResponse = new AnalyticsResponse();
        analyticsResponse.setActiveStudents((activeStudents.size()));
        analyticsResponse.setTotalAppointments((totalAppointments.size()));
        analyticsResponse.setTotalStudents((totalStudents.size()));
        analyticsResponse.setTotalUniversities(totalUniversities.size());
        analyticsResponse.setTotalRepresentatives(totalRepresentatives.size());

        // Process appointments by date
        if(totalAppointmentsByDate.size()>0) {
            ArrayList count = new ArrayList();
            ArrayList date = new ArrayList();
            for (Object[] result : totalAppointmentsByDate) {
                BigInteger appointmentsCount = (BigInteger) result[0];  // First element: count of appointments
                java.sql.Date countDate = (java.sql.Date) result[1];  // Second element: appointment date
                count.add(appointmentsCount);
                date.add(countDate);
                System.out.println("Date: " + countDate + " | Appointments: " + appointmentsCount);
            }
            //JSONObject totalAppointmentsByDateObj = new JSONObject();
            Map<String, ArrayList> totalAppointmentsByDateObj = new HashMap<>();
            totalAppointmentsByDateObj.put("count", count);
            totalAppointmentsByDateObj.put("dates", date);
            System.out.println("totalAppointmentsByDateObj: " + totalAppointmentsByDateObj);
            analyticsResponse.setTotalAppointmentsByDate(totalAppointmentsByDateObj);
        }

        // Process appointments by date and university
        if(countAppointmentsByUniversityDate.size()>0) {
            ArrayList count = new ArrayList();
            ArrayList date = new ArrayList();
            ArrayList universities = new ArrayList();
            for (Object[] result : countAppointmentsByUniversityDate) {
                BigInteger appointmentsCount = (BigInteger) result[0];
                String university = (String) result[1];
                java.sql.Date countDate = (java.sql.Date) result[2];
                count.add(appointmentsCount);
                date.add(countDate);
                universities.add(university);
                System.out.println("Date: " + countDate + " | Appointments: " + appointmentsCount);
            }
            //JSONObject totalAppointmentsByDateObj = new JSONObject();
            Map<String, ArrayList> totalAppointmentsByDateObj = new HashMap<>();
            totalAppointmentsByDateObj.put("count", count);
            totalAppointmentsByDateObj.put("dates", date);
            totalAppointmentsByDateObj.put("universities", universities);
            System.out.println("totalAppointmentsByDateObj: " + totalAppointmentsByDateObj);
            analyticsResponse.setCountAppointmentsByUniversityDate(totalAppointmentsByDateObj);
        }
        log.info("JsonObject={}",analyticsResponse);
        ResponseEntity<AnalyticsResponse> entity = new ResponseEntity<>(analyticsResponse, HttpStatus.ACCEPTED);
        return entity;
    }
}
