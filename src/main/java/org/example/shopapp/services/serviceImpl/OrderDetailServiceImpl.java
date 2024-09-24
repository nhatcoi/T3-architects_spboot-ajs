package org.example.shopapp.services.serviceImpl;

import org.example.shopapp.dtos.OrderDetailRequest;
import org.example.shopapp.entities.OrderDetail;
import org.example.shopapp.exceptions.DataNotFoundException;

import java.util.List;

public interface OrderDetailServiceImpl {
    OrderDetail createOrderDetail(OrderDetailRequest orderDetailRequest) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailRequest orderDetailRequest) throws DataNotFoundException;
    void deleteOrderDetail(Long id);
    OrderDetail getOrderDetailById(Long id) throws DataNotFoundException;
    List<OrderDetail> getAllOrderDetails(Long id) throws DataNotFoundException;
}
