package com.sliit.lanka_mart.observer;

import com.sliit.lanka_mart.model.Product;

public interface StockObserver {
    void update(Product product, String message);
}