package com.tracker.placement.controller;

import com.tracker.placement.model.Application;
import com.tracker.placement.model.Status;
import com.tracker.placement.model.Student;
import com.tracker.placement.service.ApplicationService;
import com.tracker.placement.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final StudentService studentService;

    @Autowired
    public ApplicationController(ApplicationService applicationService, StudentService studentService) {
        this.applicationService = applicationService;
        this.studentService = studentService;
    }

    @GetMapping("/add")
    public String showAddForm(HttpSession session, Model model) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        model.addAttribute("application", new Application());
        model.addAttribute("statuses", Status.values());
        return "application/add";
    }

    @PostMapping("/add")
    public String addApplication(@ModelAttribute Application application, HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        // Reload student entity and attach it to the application
        Optional<Student> studentOpt = studentService.getStudentById(loggedInStudent.getId());
        if (studentOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        application.setStudent(studentOpt.get());
        applicationService.saveApplication(application);

        return "redirect:/dashboard?added=true";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, HttpSession session, Model model) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        Optional<Application> appOpt = applicationService.getApplicationById(id);
        if (appOpt.isEmpty()) {
            return "redirect:/dashboard?error=NotFound";
        }

        Application application = appOpt.get();
        // Secure validation: check if application belongs to the logged-in student
        if (!application.getStudent().getId().equals(loggedInStudent.getId())) {
            return "redirect:/dashboard?error=Unauthorized";
        }

        model.addAttribute("application", application);
        model.addAttribute("statuses", Status.values());
        return "application/edit";
    }

    @PostMapping("/edit/{id}")
    public String editApplication(@PathVariable Long id, @ModelAttribute Application formApp, HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        Optional<Application> appOpt = applicationService.getApplicationById(id);
        if (appOpt.isEmpty()) {
            return "redirect:/dashboard?error=NotFound";
        }

        Application existingApp = appOpt.get();
        // Secure validation: check if application belongs to the logged-in student
        if (!existingApp.getStudent().getId().equals(loggedInStudent.getId())) {
            return "redirect:/dashboard?error=Unauthorized";
        }

        // Update fields
        existingApp.setCompanyName(formApp.getCompanyName());
        existingApp.setRole(formApp.getRole());
        existingApp.setPackageLpa(formApp.getPackageLpa());
        existingApp.setStatus(formApp.getStatus());
        existingApp.setDateApplied(formApp.getDateApplied());
        existingApp.setRoundsCleared(formApp.getRoundsCleared());
        existingApp.setNotes(formApp.getNotes());

        applicationService.saveApplication(existingApp);

        return "redirect:/dashboard?updated=true";
    }

    @GetMapping("/delete/{id}")
    public String deleteApplication(@PathVariable Long id, HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent == null) {
            return "redirect:/login";
        }

        Optional<Application> appOpt = applicationService.getApplicationById(id);
        if (appOpt.isEmpty()) {
            return "redirect:/dashboard?error=NotFound";
        }

        Application application = appOpt.get();
        // Secure validation: check if application belongs to the logged-in student
        if (!application.getStudent().getId().equals(loggedInStudent.getId())) {
            return "redirect:/dashboard?error=Unauthorized";
        }

        applicationService.deleteApplication(id);
        return "redirect:/dashboard?deleted=true";
    }
}
