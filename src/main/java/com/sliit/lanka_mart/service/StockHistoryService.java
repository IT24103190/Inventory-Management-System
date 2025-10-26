package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.StockHistory;
import com.sliit.lanka_mart.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;

    public List<StockHistory> getAllStockHistory() {
        return stockHistoryRepository.findAll();
    }

    public StockHistory getStockHistoryById(Integer historyId) {
        return stockHistoryRepository.findById(historyId)
                .orElseThrow(() -> new RuntimeException("Stock History not found with ID: " + historyId));
    }

    public List<StockHistory> getStockHistoryByProduct(String productId) {
        return stockHistoryRepository.findByProduct_ProductId(productId);
    }

    public List<StockHistory> getStockHistoryByUser(String userId) {
        return stockHistoryRepository.findByUser_UserId(userId);
    }

    public List<StockHistory> getStockHistoryByChangeType(String changeType) {
        return stockHistoryRepository.findByChangeType(changeType);
    }

    public List<StockHistory> getStockHistoryByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return stockHistoryRepository.findByChangeDateBetween(startDate, endDate);
    }

    public List<StockHistory> getStockHistoryByReference(String referenceId) {
        return stockHistoryRepository.findByReferenceId(referenceId);
    }
}