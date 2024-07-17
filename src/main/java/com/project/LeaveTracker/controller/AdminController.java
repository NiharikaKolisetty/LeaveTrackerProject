package com.project.LeaveTracker.controller;

import com.project.LeaveTracker.services.DepartmentService;
import com.project.LeaveTracker.services.EmployeeService;
import com.project.LeaveTracker.services.LeaveRequestService;
import com.project.LeaveTracker.services.LeaveTypeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        long totalDepartments = departmentService.getTotalCount();
        long totalLeaveTypes = leaveTypeService.getTotalCount();
        long totalEmployees = employeeService.getTotalCount();
        long pendingLeaves = leaveRequestService.getTotalPendingLeavesCount();
        long approvedLeaves = leaveRequestService.getTotalApprovedLeavesCount();
        long declinedLeaves = leaveRequestService.getTotalDeclinedLeavesCount();
        model.addAttribute("totalDepartments", totalDepartments);
        model.addAttribute("totalLeaveTypes", totalLeaveTypes);
        model.addAttribute("totalEmployees", totalEmployees);
        model.addAttribute("pendingLeaves", pendingLeaves);
        model.addAttribute("approvedLeaves", approvedLeaves);
        model.addAttribute("declinedLeaves", declinedLeaves);
        return "admin_dashboard";

    }


}
