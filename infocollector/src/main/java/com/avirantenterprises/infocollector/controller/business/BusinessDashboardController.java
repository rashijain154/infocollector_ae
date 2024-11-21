package com.avirantenterprises.infocollector.controller.business;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class BusinessDashboardController {

    @GetMapping("/business-dashboard")
    public String showBusinessDashboard(Model model) {
        model.addAttribute("title", "Business Dashboard");
        return "business-dashboard";
    }
}

