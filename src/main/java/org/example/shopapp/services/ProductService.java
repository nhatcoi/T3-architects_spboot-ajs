package org.example.shopapp.services;

import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.CategoryDTO;
import org.example.shopapp.dtos.ProductDTO;
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
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category cate = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .categoryId(cate)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Product productToUpdate = getProductById(id);
        assert productToUpdate != null;
        // copy data from productDTO to productToUpdate
        // co the su dung ModelMapper
        productToUpdate = Product.builder()
                .id(id)
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .categoryId(categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new DataNotFoundException("Category not found with id: " + productDTO.getCategoryId())))
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
        // get all products with page and limit
        return productRepository.findAll(pageRequest);
    }

    @Override
    public boolean isProductExist(Long id) {
        return false;
    }

    private ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException {
        Product existingProduct = productRepository
                .findById(productImageDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException
                        ("Product not found with id: " + productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        return productImageRepository.save(newProductImage);
    }
}
