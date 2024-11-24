package com.avirantenterprises.infocollector.controller.business;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class BusinessDashboardController {

    @GetMapping("/business-dashboard")
    public String showBusinessDashboard(Model model) {
        model.addAttribute("title", "Business Dashboard");
        return "business-dashboard";  // This will return the 'business-dashboard.html' view
    }

    // Mapping for the Image Upload Page with a unique path
    @GetMapping("/business-dashboard/upload-image")  // Unique path for the image upload page
    public String showUploadImagePage(Model model) {
        model.addAttribute("title", "Upload Image");
        return "upload-image";  // This will return the 'upload-image.html' view
    }
}
