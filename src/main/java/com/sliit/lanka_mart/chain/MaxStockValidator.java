package com.sliit.lanka_mart.chain;

import com.sliit.lanka_mart.model.Product;
import org.springframework.stereotype.Component;

@Component
public class MaxStockValidator extends StockValidator {

    private static final int MAX_STOCK = 10000;

    @Override
    public void validate(Product product, Integer quantityChange) {
        int newQuantity = product.getQuantityInStock() + quantityChange;
        if (newQuantity > MAX_STOCK) {
            throw new RuntimeException("❌ Stock exceeds maximum limit of " + MAX_STOCK);
        }
        validateNext(product, quantityChange);
    }
}