package com.project.LeaveTracker.controller;

import com.project.LeaveTracker.entity.LeaveType;
import com.project.LeaveTracker.repository.LeaveTypeRepository;
import com.project.LeaveTracker.services.LeaveTypeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;

    @GetMapping("/admin/leaveType")
    public String leaveType(Model model){
        List<LeaveType> leaveTypes = leaveTypeService.getAllLeaveTypes();
        model.addAttribute("leaveTypes", leaveTypes);
        return "leave_type";
    }

    @GetMapping("/admin/leaveType/addLeave")
    public String addLeave(Model model) {
        model.addAttribute("leaveType", new LeaveType()); // Initialize LeaveType for the form
        return "add_leave";
    }

    @PostMapping("/saveLeaveType")
    public String saveLeaveType(@RequestParam("leaveType") String leaveType,
                                @RequestParam("shortDescription") String shortDescription,
                                RedirectAttributes redirectAttributes) {
        LeaveType newLeaveType = new LeaveType();
        newLeaveType.setLeaveType(leaveType);
        newLeaveType.setShortDescription(shortDescription);
        leaveTypeService.save(newLeaveType);
        redirectAttributes.addFlashAttribute("message", "Leave Type added successfully!");
        return "redirect:/admin/leaveType"; // Redirect after successful save
    }

    @DeleteMapping("/admin/leaveType/deleteLeaveType/{id}")
    public String deleteLeaveType(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        leaveTypeService.deleteLeaveTypeById(id);
        redirectAttributes.addFlashAttribute("message", "Leave type deleted successfully.");
        return "redirect:/admin/leaveType";
    }

    @GetMapping("/admin/leaveType/editLeaveType/{id}")
    public String editLeaveType(@PathVariable Long id, Model model) {
        LeaveType leaveType = leaveTypeService.findById(id);
        model.addAttribute("leaveType", leaveType);
        return "edit_leave_type"; // Return the edit form view
    }

    @PostMapping("/admin/leaveType/updateLeaveType/{id}")
    public String updateLeaveType(@PathVariable Long id,
                                  @RequestParam("leaveType") String leaveType,
                                  @RequestParam("shortDescription") String shortDescription,
                                  RedirectAttributes redirectAttributes) {
        LeaveType leaveTypeToUpdate = leaveTypeService.findById(id);
        if (leaveTypeToUpdate != null) {
            leaveTypeToUpdate.setLeaveType(leaveType);
            leaveTypeToUpdate.setShortDescription(shortDescription);
            leaveTypeService.save(leaveTypeToUpdate);
            redirectAttributes.addFlashAttribute("message", "Leave type updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Leave type not found!");
        }
        return "redirect:/admin/leaveType";
    }
}
