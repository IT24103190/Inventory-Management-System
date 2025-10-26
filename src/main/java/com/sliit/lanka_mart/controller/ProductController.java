package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.Product;
import com.sliit.lanka_mart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    // UC-01: View Real-Time Stock Levels
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    // UC-02: Check Stock Availability
    @GetMapping("/{productId}/availability")
    public ResponseEntity<StockAvailabilityResponse> checkStockAvailability(
            @PathVariable String productId,
            @RequestParam Integer quantity) {
        Product product = productService.getProductById(productId);
        boolean available = product.getQuantityInStock() >= quantity;

        return ResponseEntity.ok(new StockAvailabilityResponse(
                product.getProductId(),
                product.getProductName(),
                product.getQuantityInStock(),
                quantity,
                available,
                product.isLowStock()
        ));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String productId,
            @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(productId, product));
    }

    // UC-03: Update Stock Records
    @PutMapping("/{productId}/stock")
    public ResponseEntity<Void> updateStock(
            @PathVariable String productId,
            @RequestBody StockUpdateRequest request) {
        productService.updateStock(
                productId,
                request.quantityChange(),      // Use quantityChange() for record
                request.changeType(),
                request.userId(),
                request.referenceId()
        );
        return ResponseEntity.ok().build();
    }

    // ✅ IMPROVED DELETE METHOD WITH ERROR HANDLING
    @DeleteMapping("/{productId}")
    public ResponseEntity<DeleteResponse> deleteProduct(@PathVariable String productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new DeleteResponse(
                    "Product deleted successfully",
                    true
            ));
        } catch (RuntimeException e) {
            // Check if it's a foreign key constraint error
            String errorMessage = e.getMessage().toLowerCase();

            if (errorMessage.contains("reference constraint") ||
                    errorMessage.contains("fk_") ||
                    errorMessage.contains("sales history") ||
                    errorMessage.contains("referenced in orders")) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new DeleteResponse(
                                "Cannot delete this product: It has sales history or is referenced in orders. " +
                                        "Consider setting stock to 0 to discontinue.",
                                false
                        ));
            }

            // Other errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DeleteResponse(
                            "Failed to delete product: " + e.getMessage(),
                            false
                    ));
        }
    }

    // UC-01: Filter by stock status
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        return ResponseEntity.ok(productService.getLowStockProducts());
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Product>> getOutOfStockProducts() {
        return ResponseEntity.ok(productService.getOutOfStockProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    // UC-06: View Item Costs and Stock Value
    @GetMapping("/total-value")
    public ResponseEntity<TotalValueResponse> getTotalStockValue() {
        BigDecimal totalValue = productService.getTotalStockValue();
        return ResponseEntity.ok(new TotalValueResponse(totalValue));
    }

    // ============================================
    // DTOs (Data Transfer Objects) - Records
    // ============================================

    /**
     * Request DTO for updating stock levels
     */
    public record StockUpdateRequest(
            Integer quantityChange,
            String changeType,
            String userId,
            String referenceId
    ) {}

    /**
     * Response DTO for stock availability check
     */
    public record StockAvailabilityResponse(
            String productId,
            String productName,
            Integer currentStock,
            Integer requestedQuantity,
            Boolean available,
            Boolean lowStock
    ) {}

    /**
     * Response DTO for total stock value
     */
    public record TotalValueResponse(
            BigDecimal totalStockValue
    ) {}

    /**
     * ✅ NEW: Response DTO for delete operations
     */
    public record DeleteResponse(
            String message,
            Boolean success
    ) {}
}