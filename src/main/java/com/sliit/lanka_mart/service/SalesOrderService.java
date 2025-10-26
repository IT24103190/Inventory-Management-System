package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.Product;
import com.sliit.lanka_mart.model.SalesOrder;
import com.sliit.lanka_mart.model.SalesOrderLine;
import com.sliit.lanka_mart.repository.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final ProductService productService;

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder getSalesOrderById(String salesOrderId) {
        return salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new RuntimeException("Sales Order not found with ID: " + salesOrderId));
    }

    @Transactional
    public SalesOrder createSalesOrder(SalesOrder salesOrder) {
        if (salesOrder.getSalesOrderId() == null || salesOrder.getSalesOrderId().isEmpty()) {
            salesOrder.setSalesOrderId(generateSalesOrderId());
        }

        // Validate stock availability before creating order
        for (SalesOrderLine line : salesOrder.getOrderLines()) {
            Product product = productService.getProductById(line.getProduct().getProductId());
            if (product.getQuantityInStock() < line.getQuantitySold()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProductName() +
                        ". Available: " + product.getQuantityInStock() + ", Required: " + line.getQuantitySold());
            }
        }

        // Set reference back to parent for each line
        for (SalesOrderLine line : salesOrder.getOrderLines()) {
            line.setSalesOrder(salesOrder);
        }

        // Save order first
        SalesOrder savedOrder = salesOrderRepository.save(salesOrder);

        // Update stock for each line item (deduct stock)
        for (SalesOrderLine line : savedOrder.getOrderLines()) {
            productService.updateStock(
                    line.getProduct().getProductId(),
                    -line.getQuantitySold(), // Negative to deduct
                    "SALES_ORDER",
                    savedOrder.getSalesExecutive().getUserId(),
                    savedOrder.getSalesOrderId()
            );
        }

        return savedOrder;
    }

    public void deleteSalesOrder(String salesOrderId) {
        salesOrderRepository.deleteById(salesOrderId);
    }

    public List<SalesOrder> getSalesOrdersBySalesExecutive(String salesExecutiveId) {
        return salesOrderRepository.findBySalesExecutive_UserId(salesExecutiveId);
    }

    public boolean checkStockAvailability(String productId, Integer quantity) {
        Product product = productService.getProductById(productId);
        return product.getQuantityInStock() >= quantity;
    }

    private String generateSalesOrderId() {
        long count = salesOrderRepository.count();
        return String.format("SO%06d", count + 1);
    }
}