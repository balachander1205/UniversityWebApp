package com.api.university.controller.appointment;

import com.api.university.entity.AppointmentsEntity;
import com.api.university.model.AppointmentModel;
import com.api.university.repository.AppointmentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/api")
@Slf4j
public class AppointmentController {

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @PostMapping("/bookAppointment")
    public ResponseEntity bookAppointment(@RequestBody AppointmentModel appointmentModel){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        appointmentsRepository.createAppointment(appointmentModel.getStudentName(), appointmentModel.getRepName(),
                appointmentModel.getUniversityName(), appointmentModel.getLocation(), appointmentModel.getAppointmentDate(), appointmentModel.getAppointmentSlot(), timestamp);
        return ResponseEntity.ok(appointmentsRepository.getAllAppointments());
    }

    @PostMapping("/getAllAppointments")
    public ResponseEntity bookAppointment(){
        return ResponseEntity.ok(appointmentsRepository.getAllAppointments());
    }

    @PostMapping("/getAppointmentsByRepname")
    public ResponseEntity getAppointmentsByRepname(@RequestParam("repname") String repname){
        List<AppointmentsEntity> listOfAppointments = appointmentsRepository.getAppointmentsByRepname(repname);
        return ResponseEntity.ok(listOfAppointments);
    }

    @RequestMapping("/appointmentDetails")
    public String appointmentDetails(HttpSession httpSession, Model model, RedirectAttributes redir) {
        log.info("appointmentDetails:userId={}",httpSession.getAttribute("userId"));
        String userId = httpSession.getAttribute("userId").toString();
        model.addAttribute("userId",userId);
        return "appointments";
    }
}
