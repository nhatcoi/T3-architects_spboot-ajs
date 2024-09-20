package org.example.shopapp.services.serviceImpl;

import org.example.shopapp.dtos.CategoryRequest;
import org.example.shopapp.entities.Category;

import java.util.List;
public interface CategoryServiceImpl {
    Category createCategory(CategoryRequest category);
    Category updateCategory(Long id, CategoryRequest category);
    void deleteCategoryById(Long id);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();

}
