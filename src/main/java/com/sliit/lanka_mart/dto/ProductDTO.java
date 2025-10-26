package com.sliit.lanka_mart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productId;
    private String productName;
    private String description;
    private Integer categoryId;
    private String categoryName;
    private Integer quantityInStock;
    private Integer reorderThreshold;
    private BigDecimal unitCost;
    private BigDecimal unitPrice;
    private Boolean lowStock;
    private BigDecimal totalStockValue;
}