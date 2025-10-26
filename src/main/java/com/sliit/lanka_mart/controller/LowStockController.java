package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.Product;
import com.sliit.lanka_mart.service.LowStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/low-stock")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LowStockController {

    private final LowStockService lowStockService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        return ResponseEntity.ok(lowStockService.getLowStockProducts());
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Product>> getOutOfStockProducts() {
        return ResponseEntity.ok(lowStockService.getOutOfStockProducts());
    }

    @GetMapping("/needing-restock")
    public ResponseEntity<List<Product>> getProductsNeedingRestock() {
        return ResponseEntity.ok(lowStockService.getProductsNeedingRestock());
    }

    @GetMapping("/count")
    public ResponseEntity<LowStockCountResponse> getLowStockCount() {
        return ResponseEntity.ok(new LowStockCountResponse(
                lowStockService.getLowStockCount(),
                lowStockService.getOutOfStockCount()
        ));
    }

    record LowStockCountResponse(int lowStockCount, int outOfStockCount) {}
}




