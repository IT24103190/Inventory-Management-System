package com.sliit.lanka_mart.repository;

import com.sliit.lanka_mart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // ✅ Only show non-deleted products
    @Query("SELECT p FROM Product p WHERE p.isDeleted = false")
    List<Product> findAll();

    @Query("SELECT p FROM Product p WHERE p.productId = :productId AND p.isDeleted = false")
    Optional<Product> findById(String productId);

    Optional<Product> findByProductName(String productName);

    List<Product> findByCategory_CategoryId(Integer categoryId);

    @Query("SELECT p FROM Product p WHERE p.quantityInStock <= p.reorderThreshold AND p.isDeleted = false")
    List<Product> findLowStockProducts();

    @Query("SELECT p FROM Product p WHERE p.quantityInStock = 0 AND p.isDeleted = false")
    List<Product> findOutOfStockProducts();

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) AND p.isDeleted = false")
    List<Product> searchByProductName(String keyword);
}