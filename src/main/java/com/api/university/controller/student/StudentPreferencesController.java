package com.api.university.controller.student;

import com.api.university.service.StudentPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class StudentPreferencesController {

    @Autowired
    StudentPreferenceService studentPreferenceService;

    @PostMapping(value = "/getAllPreferences", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getAllPreferences() {
        return ResponseEntity.ok(studentPreferenceService.getAllPreferences());
    }

    @GetMapping(value = "/preferences", produces = MediaType.APPLICATION_JSON_VALUE)
    public String preferences() {
        return "preferences";
    }
}
