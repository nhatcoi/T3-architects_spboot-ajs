package org.example.shopapp.services.serviceImpl;

import org.example.shopapp.dtos.CategoryDTO;
import org.example.shopapp.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;
public interface CategoryServiceImpl {
    Category createCategory(CategoryDTO category);
    Category updateCategory(Long id, CategoryDTO category);
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();

}
