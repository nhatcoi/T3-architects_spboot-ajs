package org.example.shopapp.services.serviceImpl;

import org.example.shopapp.dtos.OrderRequest;
import org.example.shopapp.entities.Order;
import org.example.shopapp.exceptions.DataNotFoundException;

import java.util.List;

public interface OrderServiceImpl {
    Order createOrder(OrderRequest orderRequest);
    Order updateOrder(Long id, OrderRequest orderRequest);
    void deleteOrder(Long id) throws DataNotFoundException;
    Order getOrderById(Long id) throws DataNotFoundException;
    List<Order> getAllOrdersById(Long id);
}
