package com.fakestoreapi.clone.application.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequest {
    private String title;
    private BigDecimal price;
    private String description;
    private String image;
    private Integer categoryId;
}