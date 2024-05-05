package com.api.university.controller.student;

import com.api.university.model.StudentModel;
import com.api.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/createStudent")
    public ResponseEntity createStudent(@RequestBody StudentModel studentModel) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        studentRepository.insertStudent(studentModel.getUniversityname(), studentModel.getStudentname(),
                studentModel.getLocation(), studentModel.getStudentlocation(),
                studentModel.getPhonenumber(), studentModel.getEmail(), studentModel.getFeedback(), timestamp);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/studentDetails")
    public String studentDetails(HttpSession httpSession, Model model, RedirectAttributes redir) {
        String userId = httpSession.getAttribute("userId").toString();
        model.addAttribute("userId",userId);
        return "students";
    }

    @PostMapping("/getAllStudents")
    public ResponseEntity getAllStudents(){
        return ResponseEntity.ok(studentRepository.getAllStudents());
    }
}
