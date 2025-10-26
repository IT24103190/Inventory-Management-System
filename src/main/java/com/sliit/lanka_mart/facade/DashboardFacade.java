package com.sliit.lanka_mart.facade;

import com.sliit.lanka_mart.model.Product;
import com.sliit.lanka_mart.model.StockHistory;
import com.sliit.lanka_mart.service.*;
import com.sliit.lanka_mart.strategy.StockValuationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DashboardFacade {

    private final ProductService productService;
    private final PurchaseOrderService purchaseOrderService;
    private final SalesOrderService salesOrderService;
    private final StockHistoryService stockHistoryService;
    private final StockValuationContext valuationContext;

    public DashboardSummary getCompleteDashboardSummary(String valuationStrategy) {
        var products = productService.getAllProducts();
        var lowStock = productService.getLowStockProducts();
        var outOfStock = productService.getOutOfStockProducts();
        var purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        var salesOrders = salesOrderService.getAllSalesOrders();
        var recentHistory = stockHistoryService.getAllStockHistory()
                .stream()
                .limit(10)
                .toList();

        BigDecimal stockValue = valuationContext.calculateValue(products, valuationStrategy);

        return new DashboardSummary(
                products.size(),
                products.stream().mapToInt(p -> p.getQuantityInStock()).sum(),
                lowStock.size(),
                outOfStock.size(),
                stockValue,
                purchaseOrders.size(),
                salesOrders.size(),
                lowStock,
                recentHistory
        );
    }

    public Map<String, Object> getInventoryHealthMetrics() {
        var products = productService.getAllProducts();

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalProducts", products.size());
        metrics.put("stockTurnoverRate", 4.5);
        metrics.put("fillRate", calculateFillRate(products));
        metrics.put("criticalItems", productService.getOutOfStockProducts().size());
        metrics.put("healthScore", calculateHealthScore(products));

        return metrics;
    }

    private double calculateFillRate(List<Product> products) {
        long inStock = products.stream()
                .filter(p -> p.getQuantityInStock() > 0)
                .count();
        return products.isEmpty() ? 0 : (double) inStock / products.size() * 100;
    }

    private double calculateHealthScore(List<Product> products) {
        if (products.isEmpty()) return 0;

        long healthy = products.stream()
                .filter(p -> !p.isLowStock())
                .count();

        return (double) healthy / products.size() * 100;
    }

    public record DashboardSummary(
            int totalProducts,
            int totalStock,
            int lowStockCount,
            int outOfStockCount,
            BigDecimal stockValue,
            int totalPurchaseOrders,
            int totalSalesOrders,
            List<Product> alertProducts,
            List<StockHistory> recentActivity
    ) {}
}