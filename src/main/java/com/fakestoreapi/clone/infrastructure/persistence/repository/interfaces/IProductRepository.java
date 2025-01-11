package com.fakestoreapi.clone.infrastructure.persistence.repository.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fakestoreapi.clone.infrastructure.persistence.entities.ProductEntity;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.category.name = :categoryName")
    List<ProductEntity> findByCategoryName(@Param("categoryName") String categoryName);
}