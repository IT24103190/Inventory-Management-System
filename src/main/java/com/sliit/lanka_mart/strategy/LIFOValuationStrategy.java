package com.sliit.lanka_mart.strategy;

import com.sliit.lanka_mart.model.Product;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
public class LIFOValuationStrategy implements StockValuationStrategy {

    @Override
    public BigDecimal calculateStockValue(List<Product> products) {
        return products.stream()
                .map(p -> p.getUnitPrice().multiply(new BigDecimal(p.getQuantityInStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String getStrategyName() {
        return "LIFO";
    }
}