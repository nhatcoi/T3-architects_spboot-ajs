package org.example.shopapp.controllers;

//import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.example.shopapp.dtos.ProductRequest;
import org.example.shopapp.dtos.ProductImageDTO;
import org.example.shopapp.dtos.responses.ProductListResponse;
import org.example.shopapp.dtos.responses.ProductResponse;
import org.example.shopapp.entities.Product;
import org.example.shopapp.entities.ProductImage;
import org.example.shopapp.exceptions.DataNotFoundException;
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

        return ResponseEntity.ok().body(ProductListResponse.builder()
                .products(productResponses)
                .totalPage(totalPages)
                .build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) throws DataNotFoundException {
        Product product = productService.getProductById(productId);
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        return ResponseEntity.ok().body(productResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long productId, @RequestBody ProductRequest productRequest) {
        // request to entity to db
        Product updatedProduct;
        try {
            updatedProduct = productService.updateProduct(productId, productRequest);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // entity to response
        ProductResponse productResponse = modelMapper.map(updatedProduct, ProductResponse.class);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId) throws DataNotFoundException {
        Product product = productService.getProductById(productId);
        productService.deleteProduct(productId);
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Delete product : " + productResponse);
    }


    // Task data faker
//    @PostMapping("/generateFakeProducts")
//    public ResponseEntity<?> generateFakeProducts()  {
//        Faker faker = new Faker();
//        for (int i = 0; i < 100000; i++) {
//            ProductRequest productRequest = ProductRequest.builder()
//                    .name(faker.commerce().productName())
//                    .price((float)faker.number().randomDouble(2, 1, 1000))
//                    .description(faker.lorem().sentence())
//                    .categoryId(faker.number().numberBetween(1, 26))
//                    .build();
//            try {
//                productService.createProduct(productRequest);
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body(e.getMessage());
//            }
//        }
//        return ResponseEntity.ok("Generate fake products successfully");
//    }

}
