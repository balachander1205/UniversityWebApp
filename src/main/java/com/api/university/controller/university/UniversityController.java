package com.api.university.controller.university;

import com.api.university.model.UniversityModel;
import com.api.university.repository.UniversityRepository;
import com.api.university.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class UniversityController {

    @Autowired
    UniversityRepository universityService;

    @PostMapping("/getAllUniversities")
    public ResponseEntity getAllUniversities(){
        return ResponseEntity.ok(universityService.getAllUniversities());
    }

    @PostMapping("/addUniversity")
    public ResponseEntity addUniversity(@RequestBody UniversityModel universityModel){
        universityService.insertUniversity(universityModel.getUniversityname(), universityModel.getDescription(),
                universityModel.getLocation(), universityModel.getRepname(), universityModel.getRepname(),
                universityModel.getAdmissionintake(), universityModel.getUsername(), universityModel.getPassword());
        return ResponseEntity.ok(universityService.getAllUniversities());
    }

    @GetMapping("/universityDetails")
    public String universityDetails(HttpSession httpSession, ModelMap modelMap, RedirectAttributes redir) {
        return "university";
    }
}
