package com.academia.demo.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequest(
        @NotBlank @Size(max = 20) String code,
        @NotBlank @Size(max = 140) String name,
        @NotNull @Min(1) @Max(10) Integer credits,
        @NotBlank @Size(max = 140) String teacher
) {
}
