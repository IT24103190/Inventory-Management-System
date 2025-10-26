package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.Product;
import com.sliit.lanka_mart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LowStockService {

    private final ProductRepository productRepository;

    public List<Product> getLowStockProducts() {
        return productRepository.findAll().stream()
                .filter(product -> product.getQuantityInStock() <= product.getReorderThreshold())
                .collect(Collectors.toList());
    }

    public List<Product> getOutOfStockProducts() {
        return productRepository.findAll().stream()
                .filter(product -> product.getQuantityInStock() == 0)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsNeedingRestock() {
        return productRepository.findAll().stream()
                .filter(product -> product.getQuantityInStock() <= product.getReorderThreshold())
                .collect(Collectors.toList());
    }

    public int getLowStockCount() {
        return (int) productRepository.findAll().stream()
                .filter(product -> product.getQuantityInStock() <= product.getReorderThreshold())
                .count();
    }

    public int getOutOfStockCount() {
        return (int) productRepository.findAll().stream()
                .filter(product -> product.getQuantityInStock() == 0)
                .count();
    }
}




