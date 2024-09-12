package org.example.shopapp.reposistories;

import org.example.shopapp.entities.Order;
import org.example.shopapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
