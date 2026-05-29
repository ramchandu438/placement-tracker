package com.tracker.placement.repository;

import com.tracker.placement.model.Application;
import com.tracker.placement.model.Status;
import com.tracker.placement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentOrderByDateAppliedDesc(Student student);
    long countByStudentAndStatus(Student student, Status status);
    long countByStudent(Student student);
}
