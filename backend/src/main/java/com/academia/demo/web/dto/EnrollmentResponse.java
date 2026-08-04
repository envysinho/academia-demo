package com.academia.demo.web.dto;

import com.academia.demo.domain.EnrollmentStatus;
import java.time.OffsetDateTime;
import java.util.UUID;

public record EnrollmentResponse(
        UUID id,
        StudentResponse student,
        CourseResponse course,
        EnrollmentStatus status,
        OffsetDateTime enrolledAt
) {
}
