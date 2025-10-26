package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.PurchaseOrder;
import com.sliit.lanka_mart.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/purchase-orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    // UC-04: Receive New Orders Directly from the System
    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllPurchaseOrders());
    }

    @GetMapping("/{purchaseOrderId}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable String purchaseOrderId) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderById(purchaseOrderId));
    }

    // UC-04: Supplier views new purchase orders
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersBySupplier(@PathVariable Integer supplierId) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrdersBySupplier(supplierId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PurchaseOrder>> getPurchaseOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrdersByStatus(status));
    }

    @PostMapping
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseOrderService.createPurchaseOrder(purchaseOrder));
    }

    // UC-04: Update order status (Supplier confirms receipt)
    @PutMapping("/{purchaseOrderId}/status")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrderStatus(
            @PathVariable String purchaseOrderId,
            @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(
                purchaseOrderService.updatePurchaseOrderStatus(purchaseOrderId, request.status())
        );
    }

    // Supplier approval workflow
    @PutMapping("/{purchaseOrderId}/approve")
    public ResponseEntity<PurchaseOrder> approvePurchaseOrder(@PathVariable String purchaseOrderId) {
        return ResponseEntity.ok(purchaseOrderService.approvePurchaseOrder(purchaseOrderId));
    }

    @PutMapping("/{purchaseOrderId}/reject")
    public ResponseEntity<PurchaseOrder> rejectPurchaseOrder(@PathVariable String purchaseOrderId) {
        return ResponseEntity.ok(purchaseOrderService.rejectPurchaseOrder(purchaseOrderId));
    }

    // Complete purchase order and update stock
    @PostMapping("/{purchaseOrderId}/complete")
    public ResponseEntity<PurchaseOrder> completePurchaseOrder(@PathVariable String purchaseOrderId) {
        return ResponseEntity.ok(purchaseOrderService.completePurchaseOrder(purchaseOrderId));
    }

    @DeleteMapping("/{purchaseOrderId}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable String purchaseOrderId) {
        purchaseOrderService.deletePurchaseOrder(purchaseOrderId);
        return ResponseEntity.noContent().build();
    }

    record StatusUpdateRequest(String status) {}
}