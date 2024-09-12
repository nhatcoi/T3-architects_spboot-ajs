package org.example.shopapp.reposistories;

import org.example.shopapp.entities.Product;
import org.example.shopapp.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Product productId);
}
