package com.academia.demo.web;

import com.academia.demo.service.AcademiaService;
import com.academia.demo.web.dto.CourseRequest;
import com.academia.demo.web.dto.CourseResponse;
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
@RequestMapping("/api/courses")
public class CourseController {

    private final AcademiaService academiaService;

    public CourseController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }

    @GetMapping
    public List<CourseResponse> list() {
        return academiaService.listCourses();
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CourseRequest request) {
        CourseResponse created = academiaService.createCourse(request);
        return ResponseEntity.created(URI.create("/api/courses/" + created.id())).body(created);
    }
}
