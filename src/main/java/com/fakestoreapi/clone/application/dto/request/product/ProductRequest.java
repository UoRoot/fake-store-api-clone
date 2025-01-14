package com.fakestoreapi.clone.application.dto.request.product;


import java.math.BigDecimal;

import com.fakestoreapi.clone.application.dto.validators.CreateValidatorGroup;
import com.fakestoreapi.clone.application.dto.validators.UpdateValidatorGroup;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProductRequest {
    @NotNull(message = "The product title is mandatory", groups = { CreateValidatorGroup.class })
    @Pattern(regexp = "^(?!\\s*$).+",
        message = "Title cannot be blank when provided",
        groups = { CreateValidatorGroup.class, UpdateValidatorGroup.class })
    private String title;

    @NotNull(message = "The price is mandatory", groups = { CreateValidatorGroup.class })
    @DecimalMin(value = "0.0",
            inclusive = false,
            message = "The price must be greater than 0",
            groups = { CreateValidatorGroup.class, UpdateValidatorGroup.class })
    @Digits(integer = 10,
            fraction = 2,
            message = "The price must have a maximum of 10 whole digits and 2 decimal places",
            groups = { CreateValidatorGroup.class, UpdateValidatorGroup.class })
    private BigDecimal price;

    private String description;
    private String image;

    @NotNull(message = "Category ID is mandatory", groups = { CreateValidatorGroup.class })
    private Integer categoryId;
}
