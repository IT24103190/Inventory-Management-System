package com.sliit.lanka_mart.chain;

import com.sliit.lanka_mart.model.Product;
import org.springframework.stereotype.Component;

@Component
public class NegativeStockValidator extends StockValidator {

    @Override
    public void validate(Product product, Integer quantityChange) {
        int newQuantity = product.getQuantityInStock() + quantityChange;
        if (newQuantity < 0) {
            throw new RuntimeException("❌ Stock cannot be negative. Available: " +
                    product.getQuantityInStock() + ", Requested: " + Math.abs(quantityChange));
        }
        validateNext(product, quantityChange);
    }
}