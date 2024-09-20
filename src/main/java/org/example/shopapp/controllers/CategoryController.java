package org.example.shopapp.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.CategoryRequest;
import org.example.shopapp.dtos.responses.CategoryResponse;
import org.example.shopapp.entities.Category;
import org.example.shopapp.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.shopapp.utils.CategoryUtil.createCategoryResponse;

@RestController
//@Validated
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    // Nếu tham số truyền vào là 1 đối tượng => Data Transfer Object
    // @RequestBody: Chuyển đổi dữ liệu từ client gửi lên thành đối tượng
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest, BindingResult result) {
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errors.toString());
        }

        // no errors - request data to database
        Category cate = categoryService.createCategory(categoryRequest);

        // response data to client
        return createCategoryResponse(cate.getId(), cate.getName());
    }


    // Display all categories
    @GetMapping("") // http://localhost:8080/api/v1/categories?page=1&limit=10
    public ResponseEntity<?> getAllCategory(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit)
    {
        List<Category> categories = categoryService.getAllCategories();

        // entity -> dto response
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .toList();
        return ResponseEntity.ok(categoryResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategory(id, categoryRequest);
        // response data to client
        CategoryResponse categoryResponse = new CategoryResponse(id, categoryRequest.getName());
        return createCategoryResponse(categoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Delete categories : " + id);
    }

}
