package com.sliit.lanka_mart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "SalesOrderLines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOLineID")
    private Integer soLineId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SalesOrderID", nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @Column(name = "QuantitySold", nullable = false)
    private Integer quantitySold;

    @Column(name = "SellingPriceAtSale", nullable = false, precision = 18, scale = 2)
    private BigDecimal sellingPriceAtSale;

    @Transient
    public BigDecimal getLineTotal() {
        return sellingPriceAtSale.multiply(new BigDecimal(quantitySold));
    }
}