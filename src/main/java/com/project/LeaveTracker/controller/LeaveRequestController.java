package com.project.LeaveTracker.controller;

import com.project.LeaveTracker.entity.LeaveRequest;
import com.project.LeaveTracker.entity.LeaveType;
import com.project.LeaveTracker.services.LeaveRequestService;
import com.project.LeaveTracker.services.LeaveTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @GetMapping("/user/leaveForm")
    public String leaveForm(Model model) {
        List<LeaveType> leaveTypes = leaveTypeService.getAllLeaveTypes();
        model.addAttribute("leaveTypes", leaveTypes);
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "leave_form";
    }

    @PostMapping("/saveEmployeeLeave")
    public String saveLeaveRequest(@Valid @ModelAttribute("leaveRequest") LeaveRequest leaveRequest, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            // Re-populate the leave types
            List<LeaveType> leaveTypes = leaveTypeService.getAllLeaveTypes();
            model.addAttribute("leaveTypes", leaveTypes);
            return "leave_form";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        leaveRequest.setUsername(username);
        leaveRequest.setStatus("Pending"); // Default status

        leaveRequestService.saveLeaveRequest(leaveRequest);
        redirectAttributes.addFlashAttribute("message", "Leave request submitted successfully");
        return "redirect:/user/viewLeaveHistory";
    }

    @GetMapping("/user/viewLeaveHistory")
    public String viewLeaveHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<LeaveRequest> leaveRequests = leaveRequestService.findByUsername(username);
        model.addAttribute("leaveRequests", leaveRequests);
        return "leave_history";
    }

    @GetMapping("/admin/pendingLeaveRequests")
    public String viewPendingLeaveRequests(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<LeaveRequest> leaveRequests = leaveRequestService.findByUsername(username);
        model.addAttribute("leaveRequests", leaveRequestService.findByStatus("Pending"));
        return "pending_leave_requests";
    }

    @GetMapping("/admin/approvedLeaveRequests")
    public String viewApprovedLeaveRequests(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<LeaveRequest> leaveRequests = leaveRequestService.findByUsername(username);
        model.addAttribute("leaveRequests", leaveRequestService.findByStatus("Approved"));
        return "approved_leave_requests";
    }

    @GetMapping("/admin/declinedLeaveRequests")
    public String viewDeclinedLeaveRequests(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<LeaveRequest> leaveRequests = leaveRequestService.findByUsername(username);
        model.addAttribute("leaveRequests", leaveRequestService.findByStatus("Declined"));
        return "declined_leave_requests";
    }

    @GetMapping("/admin/viewLeaveRequests")
    public String viewLeaveRequests(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<LeaveRequest> leaveRequests = leaveRequestService.findByUsername(username);
        model.addAttribute("leaveRequests", leaveRequestService.findAll());
        return "all_leave_request";
    }

    @PostMapping("/approveLeave/{id}")
    public String approveLeave(@PathVariable Long id) {
        leaveRequestService.updateLeaveStatus(id, "Approved");
        return "redirect:/admin/viewLeaveRequests";
    }

    @PostMapping("/declineLeave/{id}")
    public String declineLeave(@PathVariable Long id) {
        leaveRequestService.updateLeaveStatus(id, "Declined");
        return "redirect:/admin/viewLeaveRequests";
    }
}
