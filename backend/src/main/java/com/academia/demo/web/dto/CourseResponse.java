package com.academia.demo.web.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CourseResponse(
        UUID id,
        String code,
        String name,
        Integer credits,
        String teacher,
        OffsetDateTime createdAt
) {
}
