package com.sliit.lanka_mart.observer;

import com.sliit.lanka_mart.model.Product;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class StockSubject {

    private final List<StockObserver> observers = new ArrayList<>();

    public void attach(StockObserver observer) {
        observers.add(observer);
    }

    public void detach(StockObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Product product, String message) {
        for (StockObserver observer : observers) {
            observer.update(product, message);
        }
    }

    public void checkAndNotify(Product product) {
        if (product.isLowStock()) {
            notifyObservers(product, "⚠️ LOW STOCK ALERT");
        }
        if (product.getQuantityInStock() == 0) {
            notifyObservers(product, "🚨 OUT OF STOCK");
        }
    }
}