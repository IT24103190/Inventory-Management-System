package com.sliit.lanka_mart.chain;

import com.sliit.lanka_mart.model.Product;
import org.springframework.stereotype.Component;

@Component
public class LowStockValidator extends StockValidator {

    @Override
    public void validate(Product product, Integer quantityChange) {
        int newQuantity = product.getQuantityInStock() + quantityChange;
        if (newQuantity <= product.getReorderThreshold() && quantityChange < 0) {
            System.out.println("⚠️ WARNING: Stock will be low after this operation");
            System.out.println("   Product: " + product.getProductName());
            System.out.println("   New quantity: " + newQuantity);
        }
        validateNext(product, quantityChange);
    }
}