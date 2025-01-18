package com.fakestoreapi.clone.application.dto.response.cart;

import java.time.LocalDate;
import java.util.List;

import com.fakestoreapi.clone.domain.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {

    private Long id;
    private Long userId;
    private LocalDate date;
    List<CartItem> products;

    @Data
    public static class CartItem {
        private Product product;
        private Integer quantity;
    }

}
