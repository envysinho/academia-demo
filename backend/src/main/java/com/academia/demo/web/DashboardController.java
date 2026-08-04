package com.academia.demo.web;

import com.academia.demo.service.AcademiaService;
import com.academia.demo.web.dto.DashboardResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {

    private final AcademiaService academiaService;

    public DashboardController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard() {
        return academiaService.dashboard();
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "academia-demo");
    }
}
