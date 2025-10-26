package com.sliit.lanka_mart.repository;

import com.sliit.lanka_mart.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {
    List<PurchaseOrder> findBySupplier_SupplierId(Integer supplierId);
    List<PurchaseOrder> findByInventoryManager_UserId(String inventoryManagerId);
    List<PurchaseOrder> findByStatus(String status);
}