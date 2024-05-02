package com.api.university.controller.login;

import com.api.university.entity.User;
import com.api.university.entity.UserEntity;
import com.api.university.model.LoginModel;
import com.api.university.repository.UsersRepository;
import com.api.university.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api")
@Slf4j
public class LoginController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserService userService;

    @PostMapping("/authenticate")
    @ResponseBody
    public ResponseEntity authenticate(@RequestBody LoginModel loginModel){
        String status = "";
        try{
            log.info("loginModel={}",loginModel.getPassword());
            String password = loginModel.getPassword();
            String username = loginModel.getUsername();
            User userEntity = usersRepository.getUserDetails(username);
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

    @RequestMapping("/home")
    public String homePage(){
        return "login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerUser(Model model, @Valid User user, BindingResult bindingResult){
        log.info("{}",model);
        log.info("User={}",user);
        if(bindingResult.hasErrors()){
            model.addAttribute("successMessage", "User registered successfully!");
            model.addAttribute("bindingResult", bindingResult);
            return "register";
        }
        List<Object> userPresentObj = userService.isUserPresent(user);
        if((Boolean) userPresentObj.get(0)){
            model.addAttribute("successMessage", userPresentObj.get(1));
            return "register";
        }
        userService.saveUser(user);
        model.addAttribute("successMessage", "User registered successfully!");
        return "login";
    }
}
