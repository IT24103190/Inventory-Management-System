package com.sliit.lanka_mart.factory;

import com.sliit.lanka_mart.model.Product;
import com.sliit.lanka_mart.model.StockHistory;
import com.sliit.lanka_mart.model.User;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class StockHistoryFactory {

    public StockHistory createPurchaseOrderHistory(Product product, User user,
                                                   Integer quantity, String referenceId) {
        StockHistory history = new StockHistory();
        history.setProduct(product);
        history.setUser(user);
        history.setChangeType("PURCHASE_ORDER");
        history.setQuantityChange(quantity);
        history.setNewQuantity(product.getQuantityInStock());
        history.setChangeDate(LocalDateTime.now());
        history.setReferenceId(referenceId);
        return history;
    }

    public StockHistory createSalesOrderHistory(Product product, User user,
                                                Integer quantity, String referenceId) {
        StockHistory history = new StockHistory();
        history.setProduct(product);
        history.setUser(user);
        history.setChangeType("SALES_ORDER");
        history.setQuantityChange(-quantity);
        history.setNewQuantity(product.getQuantityInStock());
        history.setChangeDate(LocalDateTime.now());
        history.setReferenceId(referenceId);
        return history;
    }

    public StockHistory createAdjustmentHistory(Product product, User user,
                                                Integer quantity, String reason) {
        StockHistory history = new StockHistory();
        history.setProduct(product);
        history.setUser(user);
        history.setChangeType("MANUAL_ADJUSTMENT");
        history.setQuantityChange(quantity);
        history.setNewQuantity(product.getQuantityInStock());
        history.setChangeDate(LocalDateTime.now());
        history.setReferenceId(reason);
        return history;
    }

    public StockHistory createDamagedGoodsHistory(Product product, User user,
                                                  Integer quantity, String referenceId) {
        StockHistory history = new StockHistory();
        history.setProduct(product);
        history.setUser(user);
        history.setChangeType("DAMAGED_GOODS");
        history.setQuantityChange(-quantity);
        history.setNewQuantity(product.getQuantityInStock());
        history.setChangeDate(LocalDateTime.now());
        history.setReferenceId(referenceId);
        return history;
    }
}