package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.SimplePurchaseOrder;
import com.sliit.lanka_mart.model.Supplier;
import com.sliit.lanka_mart.model.User;
import com.sliit.lanka_mart.model.Product;
import com.sliit.lanka_mart.repository.SimplePurchaseOrderRepository;
import com.sliit.lanka_mart.repository.SupplierRepository;
import com.sliit.lanka_mart.repository.UserRepository;
import com.sliit.lanka_mart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimplePurchaseOrderService {

    private final SimplePurchaseOrderRepository simplePurchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<SimplePurchaseOrder> getAllPurchaseOrders() {
        return simplePurchaseOrderRepository.findAll();
    }

    public SimplePurchaseOrder getPurchaseOrderById(Long id) {
        return simplePurchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with ID: " + id));
    }

    public SimplePurchaseOrder getPurchaseOrderByOrderNumber(String orderNumber) {
        Optional<SimplePurchaseOrder> order = simplePurchaseOrderRepository.findByOrderNumber(orderNumber);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new RuntimeException("Purchase Order not found with Order Number: " + orderNumber);
        }
    }

    @Transactional
    public SimplePurchaseOrder createPurchaseOrder(SimplePurchaseOrder purchaseOrder) {
        // Generate order number
        if (purchaseOrder.getOrderNumber() == null || purchaseOrder.getOrderNumber().isEmpty()) {
            purchaseOrder.setOrderNumber(generateOrderNumber());
        }

        // Set timestamps
        purchaseOrder.setOrderDate(LocalDateTime.now());
        purchaseOrder.setCreatedAt(LocalDateTime.now());

        // Calculate total amount
        purchaseOrder.setTotalAmount(purchaseOrder.getQuantity() * purchaseOrder.getUnitPrice());

        // Set default status if not provided
        if (purchaseOrder.getStatus() == null || purchaseOrder.getStatus().isEmpty()) {
            purchaseOrder.setStatus("PENDING");
        }

        return simplePurchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public SimplePurchaseOrder createPurchaseOrderWithDetails(
            Integer supplierId,
            String inventoryManagerId,
            String productId,
            Integer quantity) {
        
        // Get supplier details
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplierId));

        // Get user details
        User user = userRepository.findById(inventoryManagerId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + inventoryManagerId));

        // Get product details
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Create purchase order
        SimplePurchaseOrder purchaseOrder = new SimplePurchaseOrder();
        purchaseOrder.setOrderNumber(generateOrderNumber());
        purchaseOrder.setSupplierId(supplierId);
        purchaseOrder.setSupplierName(supplier.getSupplierName());
        purchaseOrder.setInventoryManagerId(inventoryManagerId);
        purchaseOrder.setInventoryManagerName(user.getFirstName() + " " + user.getLastName());
        purchaseOrder.setProductId(productId);
        purchaseOrder.setProductName(product.getProductName());
        purchaseOrder.setQuantity(quantity);
        purchaseOrder.setUnitPrice(product.getUnitPrice().doubleValue());
        purchaseOrder.setTotalAmount(quantity * product.getUnitPrice().doubleValue());
        purchaseOrder.setStatus("PENDING");
        purchaseOrder.setOrderDate(LocalDateTime.now());
        purchaseOrder.setCreatedAt(LocalDateTime.now());

        return simplePurchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public SimplePurchaseOrder updatePurchaseOrderStatus(Long id, String status) {
        SimplePurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        purchaseOrder.setStatus(status);
        return simplePurchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public SimplePurchaseOrder approvePurchaseOrder(Long id) {
        SimplePurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        if (!"PENDING".equals(purchaseOrder.getStatus())) {
            throw new RuntimeException("Only pending purchase orders can be approved");
        }
        purchaseOrder.setStatus("APPROVED");
        return simplePurchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public SimplePurchaseOrder rejectPurchaseOrder(Long id) {
        SimplePurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        if (!"PENDING".equals(purchaseOrder.getStatus())) {
            throw new RuntimeException("Only pending purchase orders can be rejected");
        }
        purchaseOrder.setStatus("REJECTED");
        return simplePurchaseOrderRepository.save(purchaseOrder);
    }

    @Transactional
    public SimplePurchaseOrder completePurchaseOrder(Long id) {
        SimplePurchaseOrder purchaseOrder = getPurchaseOrderById(id);
        if (!"APPROVED".equals(purchaseOrder.getStatus())) {
            throw new RuntimeException("Only approved purchase orders can be completed");
        }
        purchaseOrder.setStatus("COMPLETED");
        return simplePurchaseOrderRepository.save(purchaseOrder);
    }

    public void deletePurchaseOrder(Long id) {
        simplePurchaseOrderRepository.deleteById(id);
    }

    public List<SimplePurchaseOrder> getPurchaseOrdersBySupplier(Integer supplierId) {
        return simplePurchaseOrderRepository.findBySupplierId(supplierId);
    }

    public List<SimplePurchaseOrder> getPurchaseOrdersByStatus(String status) {
        return simplePurchaseOrderRepository.findByStatus(status);
    }

    public List<SimplePurchaseOrder> getPurchaseOrdersByInventoryManager(String inventoryManagerId) {
        return simplePurchaseOrderRepository.findByInventoryManagerId(inventoryManagerId);
    }

    private String generateOrderNumber() {
        long count = simplePurchaseOrderRepository.count();
        return String.format("SPO%06d", count + 1);
    }
}
