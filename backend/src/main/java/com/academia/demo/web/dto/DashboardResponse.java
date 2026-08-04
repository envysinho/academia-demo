package com.academia.demo.web.dto;

public record DashboardResponse(
        long students,
        long courses,
        long enrollments
) {
}
