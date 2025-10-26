package com.sliit.lanka_mart.observer;

import com.sliit.lanka_mart.model.Product;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DashboardNotificationObserver implements StockObserver {

    private final List<String> notifications = new ArrayList<>();

    @Override
    public void update(Product product, String message) {
        String notification = String.format("[%s] %s - Stock: %d",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                product.getProductName(),
                product.getQuantityInStock()
        );
        notifications.add(notification);
        System.out.println("📊 DASHBOARD: " + notification);
    }

    public List<String> getNotifications() {
        return new ArrayList<>(notifications);
    }
}