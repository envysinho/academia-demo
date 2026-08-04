package com.academia.demo.web;

import com.academia.demo.service.AcademiaService;
import com.academia.demo.web.dto.EnrollmentRequest;
import com.academia.demo.web.dto.EnrollmentResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final AcademiaService academiaService;

    public EnrollmentController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }

    @GetMapping
    public List<EnrollmentResponse> list() {
        return academiaService.listEnrollments();
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponse> create(@Valid @RequestBody EnrollmentRequest request) {
        EnrollmentResponse created = academiaService.createEnrollment(request);
        return ResponseEntity.created(URI.create("/api/enrollments/" + created.id())).body(created);
    }
}
