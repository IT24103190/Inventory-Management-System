package com.sliit.lanka_mart.observer;

import com.sliit.lanka_mart.model.Product;
import org.springframework.stereotype.Component;

@Component
public class SMSNotificationObserver implements StockObserver {

    @Override
    public void update(Product product, String message) {
        System.out.println("📱 SMS ALERT: " + message);
        System.out.println("   Product: " + product.getProductName());

    }
}