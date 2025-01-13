package com.fakestoreapi.clone.application.dto.request.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "The product title is mandatory")
    private String title;

    @NotNull(message = "The price is mandatory")
    @DecimalMin(value = "00", inclusive = false, message = "The price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "The price must have a maximum of 10 whole digits and 2 decimal places")
    private BigDecimal price;

    private String description;
    private String image;

    @NotNull(message = "Category ID is mandatory")
    private Integer categoryId;
}
