package com.sliit.lanka_mart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {
    private String purchaseOrderId;
    private LocalDateTime orderDate;
    private String status;
    private String supplierId;
    private String supplierName;
    private String inventoryManagerId;
    private String inventoryManagerName;
    private List<PurchaseOrderLineDTO> orderLines;
    private BigDecimal totalAmount;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseOrderLineDTO {
        private Integer lineId;
        private String productId;
        private String productName;
        private Integer quantityOrdered;
        private BigDecimal costPriceAtOrder;
        private BigDecimal lineTotal;
    }
}