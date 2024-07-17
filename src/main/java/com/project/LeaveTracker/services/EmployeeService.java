package com.project.LeaveTracker.services;

import com.project.LeaveTracker.entity.Employee;
import com.project.LeaveTracker.entity.MyUser;
import com.project.LeaveTracker.repository.EmployeeRepository;
import com.project.LeaveTracker.repository.MyUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailSenderService emailService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, MyUserRepository myUserRepository, PasswordEncoder passwordEncoder,EmailSenderService emailService) {
        this.employeeRepository = employeeRepository;
        this.myUserRepository = myUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService=emailService;
    }

    public void save(@Valid Employee employee) {
        // Save the employee
        employeeRepository.save(employee);

        // Send email with credentials
        String subject = "Welcome to the Company";
        String text = "Dear " + employee.getFirstName() + ",\n\n" +
                "Your account has been created.\n" +
                "Username: " + employee.getEmail() + "\n" +
                "Password: " + employee.getPassword() + "\n\n" +
                "Best Regards,\n Leave Manager";

        emailService.sendEmail(employee.getEmail(), subject, text);

        // Create and save the MyUser entry
        MyUser myUser = new MyUser();
        myUser.setUsername(employee.getEmail()); // Assuming email as the username
        myUser.setPassword(passwordEncoder.encode(employee.getPassword()));
        myUser.setRole("USER"); // Save role without "ROLE_" prefix

        myUserRepository.save(myUser);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            // Delete the corresponding MyUser entry
            myUserRepository.findByUsername(employee.getEmail())
                    .ifPresent(myUserRepository::delete);

            // Delete the employee
            employeeRepository.deleteById(id);
        }
    }

    public long getTotalCount(){
        return employeeRepository.count();
    }
}
