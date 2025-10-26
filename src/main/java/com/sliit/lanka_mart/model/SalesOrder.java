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
@Table(name = "SalesOrders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder {

    @Id
    @Column(name = "SalesOrderID", length = 20)
    private String salesOrderId;

    @CreatedDate
    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "CustomerName", length = 255)
    private String customerName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SalesExecutiveID", nullable = false)
    private User salesExecutive;

    // ✅ EAGER fetch කරන්න වෙනස් කරන්න
    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SalesOrderLine> orderLines = new ArrayList<>();
}