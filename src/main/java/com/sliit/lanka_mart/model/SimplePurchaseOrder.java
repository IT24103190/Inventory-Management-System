package com.sliit.lanka_mart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "SimplePurchaseOrders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimplePurchaseOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "OrderNumber", unique = true, nullable = false)
    private String orderNumber;
    
    @Column(name = "SupplierId", nullable = false)
    private Integer supplierId;
    
    @Column(name = "SupplierName", nullable = false)
    private String supplierName;
    
    @Column(name = "InventoryManagerId", nullable = false)
    private String inventoryManagerId;
    
    @Column(name = "InventoryManagerName", nullable = false)
    private String inventoryManagerName;
    
    @Column(name = "ProductId", nullable = false)
    private String productId;
    
    @Column(name = "ProductName", nullable = false)
    private String productName;
    
    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "UnitPrice", nullable = false)
    private Double unitPrice;
    
    @Column(name = "TotalAmount", nullable = false)
    private Double totalAmount;
    
    @Column(name = "Status", nullable = false)
    private String status = "PENDING";
    
    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;
    
    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (totalAmount == null) {
            totalAmount = quantity * unitPrice;
        }
    }
}


