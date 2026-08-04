package com.academia.demo.repository;

import com.academia.demo.domain.Enrollment;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

    @Override
    @EntityGraph(attributePaths = {"student", "course"})
    java.util.List<Enrollment> findAll();
}
