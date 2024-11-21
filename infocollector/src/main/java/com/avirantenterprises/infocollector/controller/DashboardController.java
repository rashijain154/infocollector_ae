package com.avirantenterprises.infocollector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/index1")
    public String dashboard(Model model) {
        return "index1";
    }
}
