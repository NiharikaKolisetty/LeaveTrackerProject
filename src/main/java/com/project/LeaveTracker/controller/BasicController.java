package com.project.LeaveTracker.controller;


import com.project.LeaveTracker.services.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BasicController {

    @Autowired
    private LeaveRequestService leaveRequestService;

//    @GetMapping("/home")
//    public String handleHome(){
//        return "home";
//    }

    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return "home_admin";
    }

    @GetMapping("/user/home")
    public String handleUserHome(){
        return "home_user";
    }

    @GetMapping("/login")
    public String handleLogin(){
        return "login";
    }

    @GetMapping("/user/dashboard")
    public String handleUserDash(Model model, Principal principal){
        String username = principal.getName(); // Get logged-in username
        int pendingLeaves = leaveRequestService.getPendingLeavesCount(username);
        int approvedLeaves = leaveRequestService.getApprovedLeavesCount(username);
        int rejectedLeaves = leaveRequestService.getRejectedLeavesCount(username);

        model.addAttribute("pendingLeaves", pendingLeaves);
        model.addAttribute("approvedLeaves", approvedLeaves);
        model.addAttribute("rejectedLeaves", rejectedLeaves);
        return "user_dashboard";
    }

    @PostMapping("/logout")
    public String handleAdminLogout(Model mode){
        return "redirect:/login";
    }


}
