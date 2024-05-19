package com.api.university.controller.university;

import com.api.university.entity.UniversityEntity;
import com.api.university.model.UniversityModel;
import com.api.university.model.UniversityResponseModel;
import com.api.university.repository.UniversityRepository;
import com.api.university.service.UniversityService;
import com.api.university.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
@Slf4j
public class UniversityController {

    @Autowired
    UniversityRepository universityService;

    @PostMapping("/getAllUniversities")
    public ResponseEntity getAllUniversities(){
        List<UniversityEntity> allUniversities = universityService.getAllUniversities();
        UniversityResponseModel universityResponseModel = new UniversityResponseModel();
        universityResponseModel.setUniversities(allUniversities);
        Map arrayList = new HashMap();
        allUniversities.forEach((entity)->{
            arrayList.put(entity.getRepname(), entity.getRepname());
        });
        log.info("reps={}",arrayList);
        //List<String> representatives = universityService.getAllUniversities().stream().map(UniversityEntity :: getRepname).collect(Collectors.toList());
        universityResponseModel.setRepresentatives(arrayList);
        log.info("universityResponseModel={}",universityResponseModel);
        return ResponseEntity.ok(universityResponseModel);
    }

    @PostMapping("/addUniversity")
    public ResponseEntity addUniversity(@RequestBody UniversityModel universityModel){
        universityService.insertUniversity(universityModel.getUniversityname(), universityModel.getDescription(),
                universityModel.getLocation(), universityModel.getRepname(), universityModel.getRepname(),
                universityModel.getAdmissionintake(), universityModel.getUsername(), universityModel.getPassword(), universityModel.getState());

        List<UniversityEntity> allUniversities = universityService.getAllUniversities();
        UniversityResponseModel universityResponseModel = new UniversityResponseModel();
        universityResponseModel.setUniversities(allUniversities);
        Map arrayList = new HashMap();
        allUniversities.forEach((entity)->{
            arrayList.put(entity.getRepname(), entity.getRepname());
        });
        universityResponseModel.setStatus(HttpStatus.ACCEPTED.toString());
        universityResponseModel.setMessage(Constants.MSG_NEW_UNIVERSITY_SUCCESS.replace("%s", universityModel.getUniversityname()));
        return ResponseEntity.ok(universityResponseModel);
    }

    @GetMapping("/universityDetails")
    public String universityDetails(HttpSession httpSession, Model model, RedirectAttributes redir) {
        String userId = httpSession.getAttribute("userId").toString();
        model.addAttribute("userId",userId);
        return "university";
    }

    @GetMapping("/dumpUniversityData")
    @ResponseBody
    public String dumpUniversityData() throws IOException {
        saveUniversity();
        return "OK";
    }

    public void saveUniversity() throws IOException, JSONException {
        List<UniversityEntity> allUniversities = new ArrayList<>();
        //allUniversities.add();
        String data = readFromFileToString("/world_universities_and_domains.json");
        JSONArray jsonObject = new JSONArray(data);
        for(int i=0; i<jsonObject.length(); i++){
            UniversityEntity universityEntity = new UniversityEntity();
            JSONObject university = (JSONObject) jsonObject.get(i);
            String country = university.getString("country");
            String name = university.getString("name");
            String alpha_two_code = university.getString("alpha_two_code");
            //String state_province = university.getString("state_province")!=null?university.getString("state_province"):"";
            if(alpha_two_code.equalsIgnoreCase("US")) {
                universityEntity.setUniversityname(name);
                universityEntity.setLocation(country);
                universityEntity.setState("");
                universityEntity.setCountrycode(alpha_two_code);
                allUniversities.add(universityEntity);
                System.out.println(university);
            }
        }
        universityService.saveAll(allUniversities);
    }

    public static String readFromFileToString(String filePath) throws IOException {
        File resource = new ClassPathResource(filePath).getFile();
        byte[] byteArray = Files.readAllBytes(resource.toPath());
        return new String(byteArray);
    }
}
