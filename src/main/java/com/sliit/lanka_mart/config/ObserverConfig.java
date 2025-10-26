package com.sliit.lanka_mart.config;

import com.sliit.lanka_mart.observer.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class ObserverConfig {

    private final StockSubject stockSubject;
    private final EmailNotificationObserver emailObserver;
    private final SMSNotificationObserver smsObserver;
    private final DashboardNotificationObserver dashboardObserver;

    @PostConstruct
    public void setupObservers() {
        stockSubject.attach(emailObserver);
        stockSubject.attach(smsObserver);
        stockSubject.attach(dashboardObserver);

        System.out.println("✓ Observer Pattern initialized - Stock monitoring active");
    }
}