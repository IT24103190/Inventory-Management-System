package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.SalesOrder;
import com.sliit.lanka_mart.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sales-orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    @GetMapping
    public ResponseEntity<List<SalesOrder>> getAllSalesOrders() {
        return ResponseEntity.ok(salesOrderService.getAllSalesOrders());
    }

    @GetMapping("/{salesOrderId}")
    public ResponseEntity<SalesOrder> getSalesOrderById(@PathVariable String salesOrderId) {
        return ResponseEntity.ok(salesOrderService.getSalesOrderById(salesOrderId));
    }

    @GetMapping("/sales-executive/{salesExecutiveId}")
    public ResponseEntity<List<SalesOrder>> getSalesOrdersBySalesExecutive(
            @PathVariable String salesExecutiveId) {
        return ResponseEntity.ok(salesOrderService.getSalesOrdersBySalesExecutive(salesExecutiveId));
    }

    // UC-02: Check Stock Availability before confirming order
    @GetMapping("/check-availability")
    public ResponseEntity<StockCheckResponse> checkStockAvailability(
            @RequestParam String productId,
            @RequestParam Integer quantity) {
        boolean available = salesOrderService.checkStockAvailability(productId, quantity);
        return ResponseEntity.ok(new StockCheckResponse(productId, quantity, available));
    }

    // UC-02: Create sales order (confirms order after stock check)
    @PostMapping
    public ResponseEntity<SalesOrder> createSalesOrder(@RequestBody SalesOrder salesOrder) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(salesOrderService.createSalesOrder(salesOrder));
    }

    @DeleteMapping("/{salesOrderId}")
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable String salesOrderId) {
        salesOrderService.deleteSalesOrder(salesOrderId);
        return ResponseEntity.noContent().build();
    }

    record StockCheckResponse(String productId, Integer requestedQuantity, Boolean available) {}
}