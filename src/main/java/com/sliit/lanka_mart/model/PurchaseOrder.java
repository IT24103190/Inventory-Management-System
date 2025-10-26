package com.sliit.lanka_mart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PurchaseOrders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @Column(name = "PurchaseOrderID", length = 20)
    private String purchaseOrderId;

    @CreatedDate
    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SupplierID", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "InventoryManagerID", nullable = false)
    private User inventoryManager;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PurchaseOrderLine> orderLines = new ArrayList<>();

    public enum OrderStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
}