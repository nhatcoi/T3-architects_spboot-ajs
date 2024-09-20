package org.example.shopapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.ProductRequest;
import org.example.shopapp.dtos.ProductImageDTO;
import org.example.shopapp.dtos.responses.ProductResponse;
import org.example.shopapp.entities.Product;
import org.example.shopapp.entities.ProductImage;
import org.example.shopapp.services.serviceImpl.ProductServiceImpl;
import org.example.shopapp.utils.File;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final ModelMapper modelMapper;


    @PostMapping("")
    public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductRequest productRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }

            // create product
            Product newProduct = productService.createProduct(productRequest);

            // response data to client
            ProductResponse productResponse = modelMapper.map(newProduct, ProductResponse.class);
            return ResponseEntity.ok().body(productResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@PathVariable("id") Long productId  , @ModelAttribute("files") List<MultipartFile> files) {
        try {
            // handle image upload
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? List.of() : files;
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                // Kiểm tra kích thước file
                if (!processFile(file)) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File size is too large! (max: 10MB)");
                }
                // Kiểm tra định dạng file
                if (!isImage(file)) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("Unsupported media type! (only support image)");
                }
                // save file
                String filename = File.storeFile(file);
                // save image to database
                ProductImage productImage = productService.createProductImage(existingProduct.getId(), ProductImageDTO
                        .builder()
                        .productId(existingProduct.getId())
                        .imageUrl(filename)
                        .build());
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private boolean processFile(MultipartFile file) {
        return file.getSize() <= 10 * 1024 * 1024;
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @GetMapping("")
    public ResponseEntity<?> getProducts(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit)
    {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("createdAt").descending());
        Page<Product> productPage = productService.getAllProducts(pageRequest);
        // get all number of page
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .toList();
        return ResponseEntity.ok().body(productResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
        return ResponseEntity.ok("Get product : " + productId);
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
