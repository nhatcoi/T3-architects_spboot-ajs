package org.example.shopapp.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.CategoryDTO;
import org.example.shopapp.entities.Category;
import org.example.shopapp.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
//@Validated
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    // Nếu tham số truyền vào là 1 đối tượng => Data Transfer Object
    // @RequestBody: Chuyển đổi dữ liệu từ client gửi lên thành đối tượng
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errors.toString());
        }

        // no errors
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("Insert categories " + categoryDTO);
    }


    // Display all categories
    @GetMapping("") // http://localhost:8080/api/v1/categories?page=1&limit=10
    public ResponseEntity<?> getAllCategory(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit)
    {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update categories : " + id + " with " + categoryDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete categories : " + id);
    }

}
