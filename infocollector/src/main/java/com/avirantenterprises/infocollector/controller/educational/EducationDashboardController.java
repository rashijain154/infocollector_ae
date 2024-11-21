package com.avirantenterprises.infocollector.controller.educational;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class EducationDashboardController {

    @GetMapping("/educationDashboard")
    public String showEducationDashboard(Model model) {
        model.addAttribute("title", "Educational Dashboard");
        return "educationDashboard";
    }
}
