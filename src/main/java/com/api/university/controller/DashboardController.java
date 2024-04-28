package com.api.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboardDetails")
    public String dashboardDetails(HttpSession httpSession, ModelMap modelMap, RedirectAttributes redir) {
        return "dashboard";
    }
}
