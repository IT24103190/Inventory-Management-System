package com.sliit.lanka_mart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "PurchaseOrderLines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLineID")
    private Integer poLineId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PurchaseOrderID", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "QuantityOrdered", nullable = false)
    private Integer quantityOrdered;

    @Transient
    public BigDecimal getLineTotal() {
        if (product != null && product.getUnitPrice() != null) {
            return product.getUnitPrice().multiply(new BigDecimal(quantityOrdered));
        }
        return BigDecimal.ZERO;
    }
}