package com.project.LeaveTracker.controller;

import com.project.LeaveTracker.entity.Department;
import com.project.LeaveTracker.entity.Employee;
import com.project.LeaveTracker.services.DepartmentService;
import com.project.LeaveTracker.services.EmployeeService;
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
public class EmployeeController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/admin/empSec")
    public String empSection(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee_section";
    }

    @GetMapping("/admin/empSec/addEmp")
    public String addEmployee(Model model) {
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        model.addAttribute("employee", new Employee());
        return "add_employee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, RedirectAttributes redirectAttributes) {
        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("message", "Employee added and credentials have been sent through email");
        return "redirect:/admin/empSec";
    }

    @DeleteMapping("/admin/empSec/Emp/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteEmployeeById(id);
        redirectAttributes.addFlashAttribute("message", "Employee deleted successfully.");
        return "redirect:/admin/empSec";
    }
}
