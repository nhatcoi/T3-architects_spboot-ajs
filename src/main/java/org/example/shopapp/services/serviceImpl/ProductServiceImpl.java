package org.example.shopapp.services.serviceImpl;

import org.example.shopapp.dtos.ProductRequest;
import org.example.shopapp.dtos.ProductImageDTO;
import org.example.shopapp.entities.Product;
import org.example.shopapp.entities.ProductImage;
import org.example.shopapp.exceptions.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductServiceImpl {
    Product createProduct(ProductRequest productRequest) throws DataNotFoundException;
    Product updateProduct(Long id, ProductRequest productRequest) throws DataNotFoundException;
    void deleteProduct(Long id);
    Product getProductById(Long id) throws DataNotFoundException;
    List<Product> getAllProducts();
    Page<Product> getAllProducts(PageRequest pageRequest);
    boolean isProductExist(Long id);
    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundException;
}
