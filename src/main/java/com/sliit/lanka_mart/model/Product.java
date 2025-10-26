package com.sliit.lanka_mart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "ProductID", length = 20)
    private String productId;

    @Column(name = "ProductName", nullable = false, length = 255)
    private String productName;

    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoryID")
    private Category category;

    @Min(0)
    @Column(name = "QuantityInStock", nullable = false)
    private Integer quantityInStock = 0;

    @Min(0)
    @Column(name = "ReorderThreshold", nullable = false)
    private Integer reorderThreshold = 0;

    @DecimalMin("0.0")
    @Column(name = "UnitCost", nullable = false, precision = 18, scale = 2)
    private BigDecimal unitCost;

    @DecimalMin("0.0")
    @Column(name = "UnitPrice", nullable = false, precision = 18, scale = 2)
    private BigDecimal unitPrice;

    // ✅ Add soft delete flag
    @Column(name = "IsDeleted", nullable = false)
    private Boolean isDeleted = false;

    @Transient
    public boolean isLowStock() {
        return quantityInStock <= reorderThreshold;
    }

    @Transient
    public BigDecimal getTotalStockValue() {
        return unitCost.multiply(new BigDecimal(quantityInStock));
    }
}