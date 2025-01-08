package com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

}