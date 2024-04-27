package com.api.university.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/api")
public class AdminController {
    @GetMapping("/admin")
    @ResponseBody
    public String getAdminPage(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin";
    }
}
