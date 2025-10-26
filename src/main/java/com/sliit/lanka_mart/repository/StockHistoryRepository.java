package com.sliit.lanka_mart.repository;

import com.sliit.lanka_mart.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Integer> {
    List<StockHistory> findByProduct_ProductId(String productId);
    List<StockHistory> findByUser_UserId(String userId);
    List<StockHistory> findByChangeType(String changeType);
    List<StockHistory> findByChangeDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<StockHistory> findByReferenceId(String referenceId);
}