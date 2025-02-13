package com.api.university.controller.representative;

import com.api.university.entity.AppointmentsEntity;
import com.api.university.entity.RepresentativeEntity;
import com.api.university.model.LoginModel;
import com.api.university.model.RepresentativeModel;
import com.api.university.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/api")
public class RepresentativeController {

    @Autowired
    RepresentativeService representativeService;

    @GetMapping("/representativeDetails")
    public String universityDetails(HttpSession httpSession, Model model, RedirectAttributes redir) {
        String userId = httpSession.getAttribute("userId").toString();
        model.addAttribute("userId",userId);
        return "representatives";
    }
    @PostMapping("/getRepresentativeAppointmentsByEmailID")
    public ResponseEntity getRepresentativeAppointmentsByEmailID(@RequestParam("email") String email){
        List<AppointmentsEntity> data = representativeService.getRepresentativeAppointmentsByEmailID(email);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/getAllRepresentatives")
    public ResponseEntity getAllRepresentatives(){
        List<RepresentativeEntity> data = representativeService.getAllRepresentatives();
        return ResponseEntity.ok(data);
    }

    @PostMapping("/authenticateRep")
    public boolean authenticateRep(@RequestBody LoginModel loginModel){
        RepresentativeEntity data = representativeService.getRepresentativeByUsername(loginModel.getUsername());
        if(data!=null){
            if(loginModel.getUsername().equals(data.getUsername()) && loginModel.getPassword().equals(data.getPassword())){
                return true;
            }
        }
        return false;
    }

    @PostMapping("/getRepresentativeByEmail")
    @ResponseBody
    public ResponseEntity getRepresentativeByEmail(@RequestParam("email") String email){
        RepresentativeEntity data = representativeService.getRepresentativeByEmail(email);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/getRepresentativeByUniversityId")
    @ResponseBody
    public ResponseEntity getRepresentativeByUniversityId(@RequestParam("universityId") String universityId){
        List<RepresentativeEntity> data = representativeService.getRepresentativeByUniversityId(universityId);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/addRepresentative")
    @ResponseBody
    public ResponseEntity addRepresentative(@RequestBody RepresentativeModel representativeModel){
        representativeService.createRepresentative(representativeModel.getRepName(), representativeModel.getRepEmail(), representativeModel.getRepMobile(),
                representativeModel.getProfilePic(), representativeModel.getUsername(), representativeModel.getPassword(), representativeModel.getUniversityID(),
                representativeModel.getAvailability());
        return ResponseEntity.ok("Representative added successfully");
    }
}
