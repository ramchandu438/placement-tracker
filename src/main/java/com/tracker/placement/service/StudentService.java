package com.tracker.placement.service;

import com.tracker.placement.model.Student;
import com.tracker.placement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student registerStudent(Student student) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered!");
        }
        student.setPassword(hashPassword(student.getPassword()));
        return studentRepository.save(student);
    }

    public Optional<Student> loginStudent(String email, String password) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            String hashedInputPassword = hashPassword(password);
            if (student.getPassword().equals(hashedInputPassword)) {
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Helper method to hash passwords securely using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
