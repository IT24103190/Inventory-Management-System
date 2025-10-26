package com.sliit.lanka_mart.strategy;

import com.sliit.lanka_mart.model.Product;
import java.math.BigDecimal;
import java.util.List;

public interface StockValuationStrategy {
    BigDecimal calculateStockValue(List<Product> products);
    String getStrategyName();
}