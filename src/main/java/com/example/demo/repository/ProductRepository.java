package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByStatus(String status, Pageable pageable);

    List<Product> findBySellerId(Long sellerId);

    @Query("SELECT p FROM Product p WHERE p.status = 'ON_SALE' AND " +
            "(p.title LIKE %:keyword% OR p.description LIKE %:keyword%)")
    Page<Product> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = 'ON_SALE' AND p.category = :category")
    Page<Product> findByCategory(@Param("category") String category, Pageable pageable);
}