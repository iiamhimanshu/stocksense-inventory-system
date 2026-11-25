package com.stocksense.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 50 , message = "Name must be at most 50 characters")
        String name,
        @Size(max = 255 , message = "Description must be at most 255 characters")
        String description
) {
}
