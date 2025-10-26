package com.sliit.lanka_mart.service;

import com.sliit.lanka_mart.model.Category;
import com.sliit.lanka_mart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new RuntimeException("Category already exists: " + category.getCategoryName());
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Integer categoryId, Category categoryDetails) {
        Category category = getCategoryById(categoryId);
        category.setCategoryName(categoryDetails.getCategoryName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}