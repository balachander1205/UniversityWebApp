package com.api.university.controller.university;

import com.api.university.entity.RepresentativeEntity;
import com.api.university.entity.UniversityEntity;
import com.api.university.model.UniversityModel;
import com.api.university.model.UniversityResponseModel;
import com.api.university.repository.UniversityRepository;
import com.api.university.service.RepresentativeService;
import com.api.university.utils.CommonUtils;
import com.api.university.utils.Constants;
import com.api.university.utils.HtmlParserUtil;
import com.api.university.utils.ImageUploadUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/api")
@Slf4j
public class UniversityController {

    @Autowired
    UniversityRepository universityService;

    @Value("${spring.file.upload.location}")
    public String fileUploadLocation;

    @Autowired
    RepresentativeService representativeService;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    HtmlParserUtil htmlParserUtil;

    @Autowired
    ImageUploadUtils imageUploadUtils;

    @PostMapping("/getAllUniversities")
    public ResponseEntity getAllUniversities() {
        List<UniversityEntity> allUniversities = universityService.getAllUniversities();
        String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        UniversityResponseModel universityResponseModel = new UniversityResponseModel();
        universityResponseModel.setUniversities(allUniversities);
        universityResponseModel.getUniversities().stream().forEach(data -> {
            List<String> imagesList = new ArrayList<>();
            // Get representatives of university
            if (data.getUniversityid() != null) {
                List<RepresentativeEntity> reps = representativeService.getRepresentativeByUniversityId(data.getUniversityid());
                data.setRepresentativeEntities(reps);
            }
            if (data.getImages() != null) {
                if (data.getImages() != null && data.getImages().length() > 0 && data.getImages().contains(",")) {
                    String[] images = data.getImages().split(",");
                    Arrays.stream(images).forEach(img -> {
                        //String actualImage = homeURL + "/api/images/" + img;
                        String actualImage = img;
                        imagesList.add(actualImage);
                    });
                } else {
                    //String actualImage = homeURL + "/api/images/" + data.getImages();
                    String actualImage = data.getImages();
                    imagesList.add(actualImage);
                }
                data.setImages(StringUtils.join(imagesList, ','));
            }
        });
        log.info("universityResponseModel={}", universityResponseModel);
        return ResponseEntity.ok(universityResponseModel);
    }

    @PostMapping("/addUniversity")
    public ResponseEntity addUniversity(@RequestBody UniversityModel universityModel){
        UniversityResponseModel universityResponseModel = new UniversityResponseModel();
        try {
            long currentTS = System.currentTimeMillis();
            String universityID = String.valueOf(currentTS);
            List<String> images = new ArrayList<>();
            if (universityModel.getImages().size() > 0) {
                universityModel.getImages().forEach(image -> {
                    String imgUrl = imageUploadUtils.uploadImageToImgBB(image.split("base64,")[1]);
                    images.add(imgUrl);
                });
            }
            universityService.insertUniversity(universityModel.getUniversityname(), universityModel.getDescription(),
                    universityModel.getLocation(), universityModel.getRepname(), universityModel.getRepname(),
                    universityModel.getAdmissionintake(), universityModel.getUsername(), universityModel.getPassword(), universityModel.getState(), Arrays.toString(images.toArray()).replaceAll("\\[|\\]", ""),
                    universityModel.getCourse(), universityModel.getIsRecommended(), universityID, universityModel.getCountry());
            if (universityModel.getRepresentatives() != null && universityModel.getRepresentatives().length() > 0) {
                JSONArray reps = new JSONArray(universityModel.getRepresentatives());
                System.out.println("Reps=" + reps);
                for (int i = 0; i < reps.length(); i++) {
                    JSONObject jsonObject = reps.getJSONObject(i);
                    System.out.println("Rep=" + jsonObject);
                    System.out.println("Availability=" + jsonObject.getString("availability"));
                    String image = jsonObject.getString("image");
                    String src = htmlParserUtil.getImageSrc(image).split("base64,")[1];
                    String imgUrl = imageUploadUtils.uploadImageToImgBB(src);
                    representativeService.createRepresentative(jsonObject.getString("repName"), jsonObject.getString("username"),
                            jsonObject.getString("phonenumber"), (imgUrl != null ? imgUrl : "https://cdn-icons-png.flaticon.com/512/4042/4042171.png"), jsonObject.getString("username"), jsonObject.getString("password"), universityID, jsonObject.getString("availability"));
                }
            }
            List<UniversityEntity> allUniversities = universityService.getAllUniversities();

            universityResponseModel.setUniversities(allUniversities);
            Map arrayList = new HashMap();
            allUniversities.forEach((entity) -> {
                arrayList.put(entity.getRepname(), entity.getRepname());
            });
            universityResponseModel.setStatus(HttpStatus.ACCEPTED.toString());
            universityResponseModel.setMessage(Constants.MSG_NEW_UNIVERSITY_SUCCESS.replace("%s", universityModel.getUniversityname()));
        }catch (Exception e){
            log.info("Excpetion: addUniversity={}",e);
            e.printStackTrace();
            universityResponseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            universityResponseModel.setMessage(Constants.MSG_NEW_UNIVERSITY_FAILED.replace("%s", universityModel.getUniversityname()));
        }
        return ResponseEntity.ok(universityResponseModel);
    }

    @PostMapping("/updateUniversity")
    public ResponseEntity updateUniversity(@RequestBody UniversityModel universityModel){
        UniversityResponseModel universityResponseModel = new UniversityResponseModel();
            try {
                log.info("university ID={}", universityModel.getUniversityID());
                universityService.updateUniversity(universityModel.getUniversityname(), universityModel.getDescription(),
                        universityModel.getLocation(), universityModel.getRepname(), universityModel.getRepname(),
                        universityModel.getAdmissionintake(), universityModel.getUsername(), universityModel.getPassword(), universityModel.getState(), "",
                        universityModel.getCourse(), universityModel.getIsRecommended(), universityModel.getUniversityID(), universityModel.getCountry());
            /*if (universityModel.getRepresentatives() != null && universityModel.getRepresentatives().length() > 0) {
                JSONArray reps = new JSONArray(universityModel.getRepresentatives());
                System.out.println("Reps="+reps);
                for (int i = 0; i < reps.length(); i++)
                {
                    JSONObject jsonObject = reps.getJSONObject(i);
                    System.out.println("Rep="+jsonObject);
                    System.out.println("Availability="+jsonObject.getString("availability"));
                    String image = jsonObject.getString("image");
                    String src = htmlParserUtil.getImageSrc(image).split("base64,")[1];
                    String imgUrl = imageUploadUtils.uploadImageToImgBB(src);
                    representativeService.createRepresentative(jsonObject.getString("repName"), jsonObject.getString("username"),
                            jsonObject.getString("phonenumber"), (imgUrl!=null?imgUrl:"https://cdn-icons-png.flaticon.com/512/4042/4042171.png"), jsonObject.getString("username"), jsonObject.getString("password"), universityID , jsonObject.getString("availability"));
                }
            }*/
                List<UniversityEntity> allUniversities = universityService.getAllUniversities();

                universityResponseModel.setUniversities(allUniversities);
                Map arrayList = new HashMap();
                allUniversities.forEach((entity) -> {
                    arrayList.put(entity.getRepname(), entity.getRepname());
                });
                universityResponseModel.setStatus(HttpStatus.ACCEPTED.toString());
                universityResponseModel.setMessage(Constants.MSG_UPDATE_UNIVERSITY_SUCCESS.replace("%s", universityModel.getUniversityname()));
            }catch (Exception e){
                log.info("Excpetion: updateUniversity={}",e);
                e.printStackTrace();
                universityResponseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
                universityResponseModel.setMessage(Constants.MSG_UPDATE_UNIVERSITY_FAILED.replace("%s", universityModel.getUniversityname()));
            }
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

    @PostMapping("/addUniversityDetails")
    public ResponseEntity addUniversityDetails(@RequestPart("university") String university, @RequestParam("file") MultipartFile[] files) {
        try {
            log.info("addUniversity");
            List<String> fileNames = new ArrayList<>();

            String allImages = "";
            // Read uploaded files
            if(files.length>0) {
                Arrays.asList(files).stream().forEach(file -> {
                    try {
                        String fileName = commonUtils.getUUID() + ".jpg";
                        Path fileNameAndPath = Paths.get(fileUploadLocation, fileName);
                        log.info("fileNameAndPath={}", fileNameAndPath);
                        Files.write(fileNameAndPath, file.getBytes());
                        fileNames.add(fileName);
                    } catch (Exception e) {
                        log.info("fileUPload Exception={}", e);
                    }
                });
                allImages = StringUtils.join(fileNames, ',');
                log.info("All Files={}", allImages);
            }

            UniversityModel universityModel = new UniversityModel();
            ObjectMapper objectMapper = new ObjectMapper();
            universityModel = objectMapper.readValue(university, UniversityModel.class);
            log.info("universityModel={}", universityModel);
            long currentTS = System.currentTimeMillis();
            String universityID = String.valueOf(currentTS);

            universityService.insertUniversity(universityModel.getUniversityname(), universityModel.getDescription(),
                    universityModel.getLocation(), universityModel.getRepname(), universityModel.getRepname(),
                    universityModel.getAdmissionintake(), universityModel.getUsername(), universityModel.getPassword(), universityModel.getState(), allImages,
                    universityModel.getCourse(), universityModel.getIsRecommended(), universityID, universityModel.getCountry());

            List<UniversityEntity> allUniversities = universityService.getAllUniversities();
            String homeURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
            UniversityResponseModel universityResponseModel = new UniversityResponseModel();
            universityResponseModel.setUniversities(allUniversities);
            universityResponseModel.getUniversities().stream().forEach(data-> {
                List<String> imagesList = new ArrayList<>();
                if(data.getImages()!=null) {
                    if (data.getImages() != null && data.getImages().length() > 0 && data.getImages().contains(",")) {
                        String[] images = data.getImages().split(",");
                        Arrays.stream(images).forEach(img -> {
                            String actualImage = homeURL + "/api/images/" + img;
                            imagesList.add(actualImage);
                        });
                    } else {
                        String actualImage = homeURL + "/api/images/" + data.getImages();
                        imagesList.add(actualImage);
                    }
                    data.setImages(StringUtils.join(imagesList, ','));
                }
            });
            universityResponseModel.setStatus(HttpStatus.ACCEPTED.toString());
            universityResponseModel.setMessage(Constants.MSG_NEW_UNIVERSITY_SUCCESS.replace("%s", universityModel.getUniversityname()));
            return ResponseEntity.ok(universityResponseModel);
        }catch (Exception e){
            log.info("Xception:addUniversityDetails={}",e);
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity getImage(@PathVariable String filename) throws IOException {
        File file = new File(fileUploadLocation + filename);
        // Path to the image file
        Path path = Paths.get(fileUploadLocation + filename);
        // Load the resource
        UrlResource resource = new UrlResource(path.toUri());
        // Return ResponseEntity with image content type
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @PostMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getCountries() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("countries_states.json");
//        File file = FileUtils.getFile("./src/main/resources/countries_states.json");
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                //new String(Files.readAllBytes(file.toPath()));
        return content;
    }
}
