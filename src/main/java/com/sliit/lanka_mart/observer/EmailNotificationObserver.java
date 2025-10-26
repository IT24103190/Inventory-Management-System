package com.sliit.lanka_mart.observer;

import com.sliit.lanka_mart.model.Product;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationObserver implements StockObserver {

    @Override
    public void update(Product product, String message) {
        System.out.println("📧 EMAIL ALERT: " + message);
        System.out.println("   Product: " + product.getProductName());
        System.out.println("   Current Stock: " + product.getQuantityInStock());
        // Production එකේ actual email එවන්න පුළුවන්
    }
}