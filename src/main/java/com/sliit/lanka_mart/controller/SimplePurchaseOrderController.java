package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.model.SimplePurchaseOrder;
import com.sliit.lanka_mart.service.SimplePurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/simple-purchase-orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SimplePurchaseOrderController {

    private final SimplePurchaseOrderService simplePurchaseOrderService;

    @GetMapping
    public ResponseEntity<List<SimplePurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(simplePurchaseOrderService.getAllPurchaseOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimplePurchaseOrder> getPurchaseOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(simplePurchaseOrderService.getPurchaseOrderById(id));
    }

    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<SimplePurchaseOrder> getPurchaseOrderByOrderNumber(@PathVariable String orderNumber) {
        return ResponseEntity.ok(simplePurchaseOrderService.getPurchaseOrderByOrderNumber(orderNumber));
    }

    @PostMapping
    public ResponseEntity<SimplePurchaseOrder> createPurchaseOrder(@RequestBody SimplePurchaseOrder purchaseOrder) {
        return ResponseEntity.ok(simplePurchaseOrderService.createPurchaseOrder(purchaseOrder));
    }

    @PostMapping("/create-with-details")
    public ResponseEntity<SimplePurchaseOrder> createPurchaseOrderWithDetails(
            @RequestParam Integer supplierId,
            @RequestParam String inventoryManagerId,
            @RequestParam String productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(simplePurchaseOrderService.createPurchaseOrderWithDetails(
                supplierId, inventoryManagerId, productId, quantity));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SimplePurchaseOrder> updatePurchaseOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(simplePurchaseOrderService.updatePurchaseOrderStatus(id, status));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<SimplePurchaseOrder> approvePurchaseOrder(@PathVariable Long id) {
        return ResponseEntity.ok(simplePurchaseOrderService.approvePurchaseOrder(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<SimplePurchaseOrder> rejectPurchaseOrder(@PathVariable Long id) {
        return ResponseEntity.ok(simplePurchaseOrderService.rejectPurchaseOrder(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<SimplePurchaseOrder> completePurchaseOrder(@PathVariable Long id) {
        return ResponseEntity.ok(simplePurchaseOrderService.completePurchaseOrder(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        simplePurchaseOrderService.deletePurchaseOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<SimplePurchaseOrder>> getPurchaseOrdersBySupplier(@PathVariable Integer supplierId) {
        return ResponseEntity.ok(simplePurchaseOrderService.getPurchaseOrdersBySupplier(supplierId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SimplePurchaseOrder>> getPurchaseOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(simplePurchaseOrderService.getPurchaseOrdersByStatus(status));
    }

    @GetMapping("/inventory-manager/{inventoryManagerId}")
    public ResponseEntity<List<SimplePurchaseOrder>> getPurchaseOrdersByInventoryManager(@PathVariable String inventoryManagerId) {
        return ResponseEntity.ok(simplePurchaseOrderService.getPurchaseOrdersByInventoryManager(inventoryManagerId));
    }
}


