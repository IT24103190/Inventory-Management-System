package com.sliit.lanka_mart.repository;

import com.sliit.lanka_mart.model.SimplePurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SimplePurchaseOrderRepository extends JpaRepository<SimplePurchaseOrder, Long> {
    
    List<SimplePurchaseOrder> findBySupplierId(Integer supplierId);
    
    List<SimplePurchaseOrder> findByStatus(String status);
    
    List<SimplePurchaseOrder> findByInventoryManagerId(String inventoryManagerId);
    
    Optional<SimplePurchaseOrder> findByOrderNumber(String orderNumber);
}
