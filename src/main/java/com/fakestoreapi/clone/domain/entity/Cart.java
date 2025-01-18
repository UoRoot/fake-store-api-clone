package com.fakestoreapi.clone.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    private Long id;
    private User user;
    private LocalDateTime date;
    private List<CartItem> products;
    
    @Data
    public static class CartItem {
        private Product product;
        private Integer quantity;
    }

}
