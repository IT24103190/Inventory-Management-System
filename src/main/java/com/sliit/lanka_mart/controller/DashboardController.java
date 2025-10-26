package com.sliit.lanka_mart.controller;

import com.sliit.lanka_mart.facade.DashboardFacade;
import com.sliit.lanka_mart.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardFacade dashboardFacade;
    private final ProductService productService;
    private final PurchaseOrderService purchaseOrderService;
    private final SalesOrderService salesOrderService;

    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getDashboardOverview(
            @RequestParam(defaultValue = "FIFO") String valuationMethod) {

        var summary = dashboardFacade.getCompleteDashboardSummary(valuationMethod);

        Map<String, Object> response = new HashMap<>();
        response.put("totalProducts", summary.totalProducts());
        response.put("totalStockUnits", summary.totalStock());
        response.put("lowStockItems", summary.lowStockCount());
        response.put("outOfStockItems", summary.outOfStockCount());
        response.put("totalStockValue", summary.stockValue());
        response.put("valuationMethod", valuationMethod);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health-metrics")
    public ResponseEntity<Map<String, Object>> getHealthMetrics() {
        return ResponseEntity.ok(dashboardFacade.getInventoryHealthMetrics());
    }

    @GetMapping("/complete-summary")
    public ResponseEntity<DashboardFacade.DashboardSummary> getCompleteSummary(
            @RequestParam(defaultValue = "FIFO") String valuationMethod) {
        return ResponseEntity.ok(
                dashboardFacade.getCompleteDashboardSummary(valuationMethod)
        );
    }

    @GetMapping("/stock-levels")
    public ResponseEntity<Map<String, Object>> getStockLevels() {
        Map<String, Object> stockData = new HashMap<>();

        var allProducts = productService.getAllProducts();
        var lowStock = productService.getLowStockProducts();
        var outOfStock = productService.getOutOfStockProducts();

        stockData.put("totalProducts", allProducts.size());
        stockData.put("lowStockCount", lowStock.size());
        stockData.put("outOfStockCount", outOfStock.size());
        stockData.put("normalStockCount", allProducts.size() - lowStock.size() - outOfStock.size());
        stockData.put("lowStockProducts", lowStock);
        stockData.put("outOfStockProducts", outOfStock);

        return ResponseEntity.ok(stockData);
    }

    @GetMapping("/orders-summary")
    public ResponseEntity<OrdersSummary> getOrdersSummary() {
        var allPurchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        var allSalesOrders = salesOrderService.getAllSalesOrders();

        long pendingPurchaseOrders = allPurchaseOrders.stream()
                .filter(po -> "PENDING".equals(po.getStatus()))
                .count();

        OrdersSummary summary = new OrdersSummary(
                allPurchaseOrders.size(),
                (int) pendingPurchaseOrders,
                allSalesOrders.size()
        );

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/stock-value-report")
    public ResponseEntity<StockValueReport> getStockValueReport() {
        var products = productService.getAllProducts();

        BigDecimal totalCostValue = products.stream()
                .map(p -> p.getUnitCost().multiply(new BigDecimal(p.getQuantityInStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSellingValue = products.stream()
                .map(p -> p.getUnitPrice().multiply(new BigDecimal(p.getQuantityInStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal potentialProfit = totalSellingValue.subtract(totalCostValue);

        StockValueReport report = new StockValueReport(
                totalCostValue,
                totalSellingValue,
                potentialProfit,
                products.size()
        );

        return ResponseEntity.ok(report);
    }

    record OrdersSummary(
            Integer totalPurchaseOrders,
            Integer pendingPurchaseOrders,
            Integer totalSalesOrders
    ) {}

    record StockValueReport(
            BigDecimal totalCostValue,
            BigDecimal totalSellingValue,
            BigDecimal potentialProfit,
            Integer totalProducts
    ) {}
}