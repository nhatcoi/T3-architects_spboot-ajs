package org.example.shopapp.reposistories;

import org.example.shopapp.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name); // principle existsBy + field name
    Page<Product> findAll(Pageable pageable); // phân trang
}
