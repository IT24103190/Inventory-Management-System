package com.sliit.lanka_mart.chain;

import com.sliit.lanka_mart.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ValidationChain {

    private final NegativeStockValidator negativeValidator;
    private final LowStockValidator lowStockValidator;
    private final MaxStockValidator maxStockValidator;
    private StockValidator chain;

    @PostConstruct
    public void init() {
        // Build the chain
        negativeValidator.setNext(maxStockValidator);
        maxStockValidator.setNext(lowStockValidator);
        chain = negativeValidator;
    }

    public void validate(Product product, Integer quantityChange) {
        chain.validate(product, quantityChange);
    }
}