package com.fakestoreapi.clone.application.dto.response.product;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private String category;
    private String image;
}
