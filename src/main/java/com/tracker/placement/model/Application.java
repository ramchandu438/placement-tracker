package com.tracker.placement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String role;

    @Column(name = "package_lpa", nullable = false)
    private Double packageLpa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "date_applied", nullable = false)
    private LocalDate dateApplied;

    @Column(name = "rounds_cleared", nullable = false)
    private Integer roundsCleared = 0;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Default Constructor
    public Application() {
    }

    // Constructor with parameters
    public Application(String companyName, String role, Double packageLpa, Status status, LocalDate dateApplied, Integer roundsCleared, String notes, Student student) {
        this.companyName = companyName;
        this.role = role;
        this.packageLpa = packageLpa;
        this.status = status;
        this.dateApplied = dateApplied;
        this.roundsCleared = roundsCleared;
        this.notes = notes;
        this.student = student;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getPackageLpa() {
        return packageLpa;
    }

    public void setPackageLpa(Double packageLpa) {
        this.packageLpa = packageLpa;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDateApplied() {
        return dateApplied;
    }

    public void setDateApplied(LocalDate dateApplied) {
        this.dateApplied = dateApplied;
    }

    public Integer getRoundsCleared() {
        return roundsCleared;
    }

    public void setRoundsCleared(Integer roundsCleared) {
        this.roundsCleared = roundsCleared;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
