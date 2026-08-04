package com.academia.demo.web.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record EnrollmentRequest(
        @NotNull UUID studentId,
        @NotNull UUID courseId
) {
}
