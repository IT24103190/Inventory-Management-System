package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.StockHistory;
import com.sliit.lanka_mart.service.StockHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stock-history")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StockHistoryController {

    private final StockHistoryService stockHistoryService;

    @GetMapping
    public ResponseEntity<List<StockHistory>> getAllStockHistory() {
        return ResponseEntity.ok(stockHistoryService.getAllStockHistory());
    }

    @GetMapping("/{historyId}")
    public ResponseEntity<StockHistory> getStockHistoryById(@PathVariable Integer historyId) {
        return ResponseEntity.ok(stockHistoryService.getStockHistoryById(historyId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockHistory>> getStockHistoryByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(stockHistoryService.getStockHistoryByProduct(productId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StockHistory>> getStockHistoryByUser(@PathVariable String userId) {
        return ResponseEntity.ok(stockHistoryService.getStockHistoryByUser(userId));
    }

    @GetMapping("/change-type/{changeType}")
    public ResponseEntity<List<StockHistory>> getStockHistoryByChangeType(@PathVariable String changeType) {
        return ResponseEntity.ok(stockHistoryService.getStockHistoryByChangeType(changeType));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<StockHistory>> getStockHistoryByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(stockHistoryService.getStockHistoryByDateRange(startDate, endDate));
    }

    @GetMapping("/reference/{referenceId}")
    public ResponseEntity<List<StockHistory>> getStockHistoryByReference(@PathVariable String referenceId) {
        return ResponseEntity.ok(stockHistoryService.getStockHistoryByReference(referenceId));
    }
}