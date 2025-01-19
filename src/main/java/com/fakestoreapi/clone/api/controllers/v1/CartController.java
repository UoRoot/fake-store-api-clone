package com.fakestoreapi.clone.api.controllers.v1;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fakestoreapi.clone.application.dto.request.cart.CartRequest;
import com.fakestoreapi.clone.application.dto.response.cart.CartResponse;
import com.fakestoreapi.clone.application.dto.validators.CreateValidatorGroup;
import com.fakestoreapi.clone.application.mapper.CartMapper;
import com.fakestoreapi.clone.application.service.interfaces.ICartService;
import com.fakestoreapi.clone.domain.entity.Cart;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;
    private final CartMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long id) {
        Cart cart = cartService.getCart(id);
        return ResponseEntity.ok().body(mapper.toResponse(cart));
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        return ResponseEntity.ok().body(cartService.getAllCarts()
                .stream()
                .map(mapper::toResponse)
                .toList());
    }

    @PostMapping
    public ResponseEntity<CartResponse> createCart(
            @Validated(CreateValidatorGroup.class) @RequestBody CartRequest request) {
        var cart = mapper.toDomain(request);
        var created = cartService.createCart(cart);
        System.out.println("Domain Cart: " + created);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        CartResponse cartResponse = mapper.toResponse(created);
        System.out.println("Response Cart: " + cartResponse);
        return ResponseEntity.created(location).body(cartResponse);
    }

}
