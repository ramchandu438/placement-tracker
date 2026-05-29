package com.tracker.placement.service;

import com.tracker.placement.model.Application;
import com.tracker.placement.model.Status;
import com.tracker.placement.model.Student;
import com.tracker.placement.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> getApplicationsByStudent(Student student) {
        return applicationRepository.findByStudentOrderByDateAppliedDesc(student);
    }

    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    public Map<String, Long> getDashboardStats(Student student) {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", applicationRepository.countByStudent(student));
        stats.put("applied", applicationRepository.countByStudentAndStatus(student, Status.APPLIED));
        stats.put("oa", applicationRepository.countByStudentAndStatus(student, Status.OA));
        stats.put("interview", applicationRepository.countByStudentAndStatus(student, Status.INTERVIEW));
        stats.put("selected", applicationRepository.countByStudentAndStatus(student, Status.SELECTED));
        stats.put("rejected", applicationRepository.countByStudentAndStatus(student, Status.REJECTED));
        return stats;
    }
}
