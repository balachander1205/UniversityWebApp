package com.api.university.controller.profile;

import com.api.university.entity.*;
import com.api.university.model.AnalyticsResponse;
import com.api.university.repository.AppointmentsRepository;
import com.api.university.repository.StudentRepository;
import com.api.university.repository.UniversityRepository;
import com.api.university.repository.UsersRepository;
import com.api.university.service.RepresentativeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@Slf4j
public class ProfileController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AppointmentsRepository appointmentsRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    RepresentativeService representativeService;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/profile")
    public String dashboardDetails(HttpSession httpSession, Model model, RedirectAttributes redir) {
        log.info("dashboard:userId={}",httpSession.getAttribute("userId"));
        String userId = httpSession.getAttribute("userId").toString();
        model.addAttribute("userId",userId);
        User userEntity = usersRepository.getUserDetails(userId);
        return "profile";
    }

    @PostMapping("/profile")
    @ResponseBody
    public ResponseEntity getProfile(HttpSession httpSession, Model model, RedirectAttributes redir) {
        log.info("dashboard:userId={}",httpSession.getAttribute("userId"));
        String userId = httpSession.getAttribute("userId").toString();
        model.addAttribute("userId",userId);
        User userEntity = usersRepository.getUserDetails(userId);
        userEntity.setPassword("");
        return new ResponseEntity(userEntity, HttpStatus.OK);
    }
}
