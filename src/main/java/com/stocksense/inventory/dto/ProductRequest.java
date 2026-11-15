package com.stocksense.inventory.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest (

    @NotBlank(message = "Name is required")
    @Size(max = 100 , message = "name must be at most 100 characters")
    String name,

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false , message = "Price must be greater than 0")
    @Digits(integer = 8 , fraction = 2 , message = "Price can have at most 8 digits and 2 decimals")
    BigDecimal price,

    @NotNull(message = "Quantity is required")
    @Min(value = 0 , message = "Quantity cannot be negative")
    Integer quantity

){}

