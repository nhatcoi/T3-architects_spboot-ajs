package org.example.shopapp.utils;

import org.example.shopapp.dtos.responses.CategoryResponse;
import org.springframework.http.ResponseEntity;

public class CategoryUtil {
    public static ResponseEntity<CategoryResponse> createCategoryResponse(Long id, String name) {
        CategoryResponse categoryResponse = new CategoryResponse(id, name);
        return ResponseEntity.ok().body(categoryResponse);
    }

    public static ResponseEntity<CategoryResponse> createCategoryResponse(CategoryResponse categoryResponse) {
        return ResponseEntity.ok().body(categoryResponse);
    }


}
