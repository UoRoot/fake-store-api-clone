package com.fakestoreapi.clone.api.controllers.v1;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fakestoreapi.clone.application.dto.request.ProductRequest;
import com.fakestoreapi.clone.application.dto.response.ProductResponse;
import com.fakestoreapi.clone.application.mapper.ProductMapper;
import com.fakestoreapi.clone.application.service.interfaces.IProductService;
import com.fakestoreapi.clone.domain.entity.Product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final ProductMapper mapper;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        var product = mapper.toProduct(request);
        var created = productService.createProduct(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(mapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts()
                .stream()
                .map(mapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return productService.getProduct(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long id) {
        Optional<Product> product = productService.deleteProduct(id);

        if (product.isPresent()) {
            return ResponseEntity.ok()
                    .body(product.map(mapper::toResponse).get());
        }

        return null;
    }

}