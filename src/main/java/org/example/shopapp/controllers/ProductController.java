package org.example.shopapp.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.ProductDTO;
import org.example.shopapp.services.ProductService;
import org.example.shopapp.services.serviceImpl.ProductServiceImpl;
import org.example.shopapp.utils.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;


    @GetMapping("")
    public ResponseEntity<?> getProducts(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit
    ) {
        return ResponseEntity.ok("Get all products");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
        return ResponseEntity.ok("Get product : " + productId);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }

            List<MultipartFile> files = Optional.ofNullable(productDTO.getFiles()).orElse(List.of());
            for (MultipartFile file : files) {
                if (!processFile(file)) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File size is too large! (max: 10MB)");
                }
                if (!isImage(file)) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("Unsupported media type! (only support image)");
                }
                String filename = File.storeFile(file);
            }

            productService.createProduct(productDTO);

            return ResponseEntity.ok("Insert product : " + productDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean processFile(MultipartFile file) {
        return file.getSize() <= 10 * 1024 * 1024;
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Update product : " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Delete product : " + id);
    }

}
