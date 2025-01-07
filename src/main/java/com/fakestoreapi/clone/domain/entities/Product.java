package com.fakestoreapi.clone.domain.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private String image;
    private String category;
    private Rating rating;

    @Data
    static class Rating {
        private String rate;
        private String count;
    }
}