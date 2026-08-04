package com.academia.demo.web.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StudentResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String documentNumber,
        OffsetDateTime createdAt
) {
}
