package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.Product;
import com.sliit.lanka_mart.model.StockHistory;
import com.sliit.lanka_mart.repository.ProductRepository;
import com.sliit.lanka_mart.repository.StockHistoryRepository;
import com.sliit.lanka_mart.factory.StockHistoryFactory;
import com.sliit.lanka_mart.chain.ValidationChain;
import com.sliit.lanka_mart.observer.StockSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final StockHistoryFactory stockHistoryFactory;
    private final ValidationChain validationChain;
    private final StockSubject stockSubject;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }

    public Product createProduct(Product product) {
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            product.setProductId(generateProductId());
        }
        Product savedProduct = productRepository.save(product);

        if (savedProduct.getQuantityInStock() > 0) {
            StockHistory history = stockHistoryFactory.createAdjustmentHistory(
                    savedProduct,
                    null,
                    savedProduct.getQuantityInStock(),
                    "INITIAL_STOCK"
            );
            stockHistoryRepository.save(history);
        }
        return savedProduct;
    }

    public Product updateProduct(String productId, Product productDetails) {
        Product product = getProductById(productId);
        product.setProductName(productDetails.getProductName());
        product.setDescription(productDetails.getDescription());
        product.setCategory(productDetails.getCategory());
        product.setReorderThreshold(productDetails.getReorderThreshold());
        product.setUnitCost(productDetails.getUnitCost());
        product.setUnitPrice(productDetails.getUnitPrice());
        return productRepository.save(product);
    }

    @Transactional
    public void updateStock(String productId, Integer quantityChange,
                            String changeType, String userId, String referenceId) {
        Product product = getProductById(productId);

        validationChain.validate(product, quantityChange);

        int newQuantity = product.getQuantityInStock() + quantityChange;
        product.setQuantityInStock(newQuantity);
        productRepository.save(product);

        stockSubject.checkAndNotify(product);

        StockHistory history = new StockHistory();
        history.setProduct(product);
        history.setChangeType(changeType);
        history.setQuantityChange(quantityChange);
        history.setNewQuantity(newQuantity);
        history.setReferenceId(referenceId);
        stockHistoryRepository.save(history);
    }

    @Transactional
    public void deleteProduct(String productId) {
        Product product = getProductById(productId);

        // ✅ Soft delete instead of hard delete
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    public List<Product> getOutOfStockProducts() {
        return productRepository.findOutOfStockProducts();
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchByProductName(keyword);
    }

    public List<Product> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    public BigDecimal getTotalStockValue() {
        return productRepository.findAll().stream()
                .map(Product::getTotalStockValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String generateProductId() {
        List<Product> allProducts = productRepository.findAll();
        int maxId = 0;

        for (Product p : allProducts) {
            try {
                String idNum = p.getProductId().replace("PRD", "");
                int num = Integer.parseInt(idNum);
                if (num > maxId) {
                    maxId = num;
                }
            } catch (Exception e) {
                // Skip
            }
        }

        return String.format("PRD%05d", maxId + 1);
    }
}