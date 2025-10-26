package com.sliit.lanka_mart.repository;

import com.sliit.lanka_mart.model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, String> {
    List<SalesOrder> findBySalesExecutive_UserId(String salesExecutiveId);
    List<SalesOrder> findByCustomerName(String customerName);
    List<SalesOrder> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}