package com.tracker.placement.controller;

import com.tracker.placement.model.Student;
import com.tracker.placement.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final StudentService studentService;

    @Autowired
    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("loggedInStudent") != null) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpSession session, Model model) {
        if (session.getAttribute("loggedInStudent") != null) {
            return "redirect:/dashboard";
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Optional<Student> studentOpt = studentService.loginStudent(email, password);
        if (studentOpt.isPresent()) {
            session.setAttribute("loggedInStudent", studentOpt.get());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(HttpSession session, Model model) {
        if (session.getAttribute("loggedInStudent") != null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("student", new Student());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Student student, Model model) {
        try {
            studentService.registerStudent(student);
            return "redirect:/login?registered=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}
