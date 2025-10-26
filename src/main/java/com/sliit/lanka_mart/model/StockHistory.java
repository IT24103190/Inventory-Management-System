package com.sliit.lanka_mart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "StockHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HistoryID")
    private Integer historyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "ChangeType", nullable = false, length = 50)
    private String changeType;

    @Column(name = "QuantityChange", nullable = false)
    private Integer quantityChange;

    @Column(name = "NewQuantity", nullable = false)
    private Integer newQuantity;

    @CreatedDate
    @Column(name = "ChangeDate", nullable = false)
    private LocalDateTime changeDate = LocalDateTime.now();

    @Column(name = "ReferenceID", length = 20)
    private String referenceId;

    public enum ChangeType {
        PURCHASE_ORDER,
        SALES_ORDER,
        MANUAL_ADJUSTMENT,
        STOCK_TRANSFER,
        DAMAGED_GOODS,
        RETURN
    }
}