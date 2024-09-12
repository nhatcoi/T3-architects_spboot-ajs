package org.example.shopapp.services.serviceImpl;

import org.example.shopapp.dtos.ProductDTO;
import org.example.shopapp.entities.Product;
import org.example.shopapp.exceptions.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductServiceImpl {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;
    Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;
    void deleteProduct(Long id);
    Product getProductById(Long id) throws DataNotFoundException;
    List<Product> getAllProducts();
    Page<Product> getAllProducts(PageRequest pageRequest);
    boolean isProductExist(Long id);
}
