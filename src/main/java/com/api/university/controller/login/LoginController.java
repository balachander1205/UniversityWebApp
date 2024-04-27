package com.api.university.controller.login;

import com.api.university.entity.UserEntity;
import com.api.university.model.LoginModel;
import com.api.university.repository.UsersRepository;
import com.api.university.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api")
@Slf4j
public class LoginController {

    @Autowired
    UsersRepository userService;

    @PostMapping("/authenticate")
    @ResponseBody
    public ResponseEntity authenticate(@RequestBody LoginModel loginModel){
        String status = "";
        try{
            log.info("loginModel={}",loginModel.getPassword());
            String password = loginModel.getPassword();
            String username = loginModel.getUsername();
            UserEntity userEntity = userService.getUserDetails(username);
            log.info("Username:={} Password:={}",username, password);
            if(userEntity.getUsername().equals(username) && userEntity.getPassword().equals(password)){
                status = "OK";
            }else {
                status = "NOT OK";
            }
        }catch (Exception e){
            e.printStackTrace();
            log.debug("authenticate:Xception={}",e);
        }
        return ResponseEntity.ok(status);
    }
}
