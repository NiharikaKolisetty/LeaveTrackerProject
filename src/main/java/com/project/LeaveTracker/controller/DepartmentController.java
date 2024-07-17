package com.project.LeaveTracker.controller;

import com.project.LeaveTracker.entity.Department;
import com.project.LeaveTracker.services.DepartmentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/admin/deptSec")
    public String departmentSection(Model model){
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "department_section";
    }

    @GetMapping("/admin/deptSec/addDept")
    public String addDept(Model model) {
        model.addAttribute("department", new Department());
        return "add_department";
    }

    @PostMapping("/saveDept")
    public String saveDept(@Valid @ModelAttribute Department department, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "add_department"; // Return the form view to show validation errors
        }
        departmentService.save(department);
        // Add a flash attribute for success message
        redirectAttributes.addFlashAttribute("message", "Department added successfully!");
        return "redirect:/admin/deptSec"; // Redirect after successful save
    }

    @DeleteMapping("/admin/deptSec/deleteDept/{id}")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        departmentService.deleteDepartmentById(id);
        redirectAttributes.addFlashAttribute("message", "Department deleted successfully.");
        return "redirect:/admin/deptSec";
    }

    @GetMapping("/admin/deptSec/editDepartment/{id}")
    public String editDepartment(@PathVariable Long id,  Model model) {
        Department department = departmentService.findById(id);
        model.addAttribute("department", department);
        return "edit_department"; // Return the edit form view
    }

    @PostMapping("/admin/deptSec/updateDepartment/{id}")
    public String updateDepartment(@PathVariable Long id, @Valid @ModelAttribute Department department, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit_department"; // Return the form view to show validation errors
        }
        departmentService.save(department);
        // Add a flash attribute for success message
        redirectAttributes.addFlashAttribute("message", "Department updated successfully!");
        return "redirect:/admin/deptSec"; // Redirect after successful update
    }
}