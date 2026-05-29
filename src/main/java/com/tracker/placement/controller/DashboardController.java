package com.tracker.placement.controller;

import com.tracker.placement.model.Application;
import com.tracker.placement.model.Student;
import com.tracker.placement.service.ApplicationService;
import com.tracker.placement.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class DashboardController {

    private final ApplicationService applicationService;
    private final StudentService studentService;

    @Autowired
    public DashboardController(ApplicationService applicationService, StudentService studentService) {
        this.applicationService = applicationService;
        this.studentService = studentService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        // Reload student to get latest database state
        Optional<Student> studentOpt = studentService.getStudentById(loggedInStudent.getId());
        if (studentOpt.isEmpty()) {
            session.invalidate();
            return "redirect:/login";
        }
        Student student = studentOpt.get();

        // Get student application records and statistics
        List<Application> applications = applicationService.getApplicationsByStudent(student);
        Map<String, Long> stats = applicationService.getDashboardStats(student);

        model.addAttribute("student", student);
        model.addAttribute("applications", applications);
        model.addAttribute("stats", stats);

        return "dashboard";
    }
}
