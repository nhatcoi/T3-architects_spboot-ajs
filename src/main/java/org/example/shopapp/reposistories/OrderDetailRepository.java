package org.example.shopapp.reposistories;

import org.example.shopapp.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);

    @Query("SELECT od FROM OrderDetail od JOIN FETCH od.order o JOIN FETCH od.product p WHERE od.id = :orderDetailId")
    Optional<OrderDetail> findWithOrderAndProduct(@Param("orderDetailId") Long orderDetailId);
}
