package com.sliit.lanka_mart.strategy;

import com.sliit.lanka_mart.model.Product;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StockValuationContext {

    private final Map<String, StockValuationStrategy> strategies;

    public StockValuationContext(List<StockValuationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        StockValuationStrategy::getStrategyName,
                        Function.identity()
                ));
    }

    public BigDecimal calculateValue(List<Product> products, String strategyName) {
        StockValuationStrategy strategy = strategies.getOrDefault(
                strategyName,
                strategies.get("FIFO")
        );
        return strategy.calculateStockValue(products);
    }
}