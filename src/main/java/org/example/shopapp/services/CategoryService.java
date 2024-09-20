package org.example.shopapp.services;

import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.CategoryRequest;
import org.example.shopapp.dtos.responses.CategoryResponse;
import org.example.shopapp.entities.Category;
import org.example.shopapp.reposistories.CategoryRepository;
import org.example.shopapp.services.serviceImpl.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceImpl {
    private final CategoryRepository categoryRepository;

    // @RequiredArgsConstructor: tạo constructor khi có final field

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        Category newCategory = Category.builder()
                .name(categoryRequest.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        Category categoryToUpdate = getCategoryById(id);
        categoryToUpdate.setName(categoryRequest.getName());
        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public void deleteCategoryById(Long id) {
        // xoá cứng
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
