package org.example.shopapp.services;

import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.ProductRequest;
import org.example.shopapp.dtos.ProductImageDTO;
import org.example.shopapp.entities.Category;
import org.example.shopapp.entities.Product;
import org.example.shopapp.entities.ProductImage;
import org.example.shopapp.exceptions.DataNotFoundException;
import org.example.shopapp.reposistories.CategoryRepository;
import org.example.shopapp.reposistories.ProductImageRepository;
import org.example.shopapp.reposistories.ProductRepository;
import org.example.shopapp.services.serviceImpl.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceImpl {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductRequest productRequest) throws DataNotFoundException {
        Category cate = categoryRepository
                .findById(productRequest.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found with id: " + productRequest.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .categoryId(cate)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) throws DataNotFoundException {
        Product productToUpdate = getProductById(id);
        assert productToUpdate != null;
        // copy data from productDTO to productToUpdate
        // co the su dung ModelMapper
        productToUpdate = Product.builder()
                .id(id)
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .thumbnail(productRequest.getThumbnail())
                .categoryId(categoryRepository.findById(productRequest.getCategoryId())
                        .orElseThrow(() -> new DataNotFoundException("Category not found with id: " + productRequest.getCategoryId())))
                .build();
        return productRepository.save(productToUpdate);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) throws DataNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        // Retrieve all products with pagination
        return productRepository.findAll(pageRequest);
    }

    @Override
    public boolean isProductExist(Long id) {
        return false;
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException {
        Product existingProduct = productRepository
                .findById(productImageDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException
                        ("Product not found with id: " + productImageDTO.getProductId()));
        // not insert more than 5 images for a product
        deleteOldImagesIfExceedsLimit(productId);
        return saveProductImage(existingProduct, productImageDTO.getImageUrl());
    }


    private void deleteOldImagesIfExceedsLimit(Long productId) {
        productImageRepository.findByProductId(productId)
                .stream()
                .limit(5)
                .forEach(productImageRepository::delete);
    }

    private ProductImage saveProductImage(Product product, String imageUrl) {
        ProductImage newProductImage = ProductImage.builder()
                .product(product)
                .imageUrl(imageUrl)
                .build();
        return productImageRepository.save(newProductImage);
    }
}
