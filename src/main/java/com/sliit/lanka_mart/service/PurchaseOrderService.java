package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.PurchaseOrder;
import com.sliit.lanka_mart.model.PurchaseOrderLine;
import com.sliit.lanka_mart.model.User;
import com.sliit.lanka_mart.repository.PurchaseOrderRepository;
import com.sliit.lanka_mart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(String purchaseOrderId) {
        return purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with ID: " + purchaseOrderId));
    }

    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getPurchaseOrderId() == null || purchaseOrder.getPurchaseOrderId().isEmpty()) {
            purchaseOrder.setPurchaseOrderId(generatePurchaseOrderId());
        }

        // Load the complete user with role information
        if (purchaseOrder.getInventoryManager() != null && purchaseOrder.getInventoryManager().getUserId() != null) {
            User completeUser = userRepository.findById(purchaseOrder.getInventoryManager().getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + purchaseOrder.getInventoryManager().getUserId()));
            purchaseOrder.setInventoryManager(completeUser);
        }

        // Set reference back to parent for each line
        for (PurchaseOrderLine line : purchaseOrder.getOrderLines()) {
            line.setPurchaseOrder(purchaseOrder);
        }

        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder updatePurchaseOrderStatus(String purchaseOrderId, String status) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(purchaseOrderId);
        purchaseOrder.setStatus(status);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public PurchaseOrder completePurchaseOrder(String purchaseOrderId) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(purchaseOrderId);

        if (!"PENDING".equals(purchaseOrder.getStatus()) && !"IN_PROGRESS".equals(purchaseOrder.getStatus())) {
            throw new RuntimeException("Purchase order cannot be completed. Current status: " + purchaseOrder.getStatus());
        }

        // Update stock for each line item
        // TODO: Implement stock update when ProductService is available
        // for (PurchaseOrderLine line : purchaseOrder.getOrderLines()) {
        //     productService.updateStock(
        //             line.getProduct().getProductId(),
        //             line.getQuantityOrdered(),
        //             "PURCHASE_ORDER",
        //             purchaseOrder.getInventoryManager().getUserId(),
        //             purchaseOrderId
        //     );
        // }

        purchaseOrder.setStatus("COMPLETED");
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public void deletePurchaseOrder(String purchaseOrderId) {
        purchaseOrderRepository.deleteById(purchaseOrderId);
    }

    public List<PurchaseOrder> getPurchaseOrdersBySupplier(Integer supplierId) {
        return purchaseOrderRepository.findBySupplier_SupplierId(supplierId);
    }

    public List<PurchaseOrder> getPurchaseOrdersByStatus(String status) {
        return purchaseOrderRepository.findByStatus(status);
    }

    @Transactional
    public PurchaseOrder approvePurchaseOrder(String purchaseOrderId) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(purchaseOrderId);
        if (!"PENDING".equals(purchaseOrder.getStatus())) {
            throw new RuntimeException("Only pending purchase orders can be approved");
        }
        
        // Update stock for each line item when approved
        for (PurchaseOrderLine line : purchaseOrder.getOrderLines()) {
            productService.updateStock(
                line.getProduct().getProductId(),
                line.getQuantityOrdered(),
                "PURCHASE_ORDER",
                purchaseOrder.getInventoryManager().getUserId(),
                purchaseOrderId
            );
        }
        
        purchaseOrder.setStatus("APPROVED");
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder rejectPurchaseOrder(String purchaseOrderId) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(purchaseOrderId);
        if (!"PENDING".equals(purchaseOrder.getStatus())) {
            throw new RuntimeException("Only pending purchase orders can be rejected");
        }
        purchaseOrder.setStatus("REJECTED");
        return purchaseOrderRepository.save(purchaseOrder);
    }

    private String generatePurchaseOrderId() {
        long count = purchaseOrderRepository.count();
        return String.format("PO%06d", count + 1);
    }
}