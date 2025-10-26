package com.sliit.lanka_mart.chain;

import com.sliit.lanka_mart.model.Product;

public abstract class StockValidator {

    protected StockValidator next;

    public void setNext(StockValidator next) {
        this.next = next;
    }

    public abstract void validate(Product product, Integer quantityChange);

    protected void validateNext(Product product, Integer quantityChange) {
        if (next != null) {
            next.validate(product, quantityChange);
        }
    }
}