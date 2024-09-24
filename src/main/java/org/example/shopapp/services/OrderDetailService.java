package org.example.shopapp.services;

import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.OrderDetailRequest;
import org.example.shopapp.entities.Order;
import org.example.shopapp.entities.OrderDetail;
import org.example.shopapp.entities.Product;
import org.example.shopapp.exceptions.DataNotFoundException;
import org.example.shopapp.reposistories.OrderDetailRepository;
import org.example.shopapp.reposistories.OrderRepository;
import org.example.shopapp.reposistories.ProductRepository;
import org.example.shopapp.services.serviceImpl.OrderDetailServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements OrderDetailServiceImpl {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    @Override
    public OrderDetail createOrderDetail(OrderDetailRequest orderDetailRequest) throws DataNotFoundException {
        // check if order exists

        Order order = orderRepository.findById(orderDetailRequest.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order not found with id: " + orderDetailRequest.getOrderId()));
        // check if product exists
        Product orderProduct = productRepository.findById(orderDetailRequest.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + orderDetailRequest.getProductId()));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(orderProduct)
                .price(orderProduct.getPrice())
                .numberOfProducts(orderDetailRequest.getNumberOfProducts())
                .totalPrice(orderProduct.getPrice() * orderDetailRequest.getNumberOfProducts())
                .color(orderDetailRequest.getColor())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(Long orderDetailId, OrderDetailRequest orderDetailRequest) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found with id: " + orderDetailId));

        // mapper request to entity
        try {
            orderDetail.setPrice(orderDetailRequest.getPrice());
            orderDetail.setNumberOfProducts(orderDetailRequest.getNumberOfProducts());
            orderDetail.setTotalPrice(orderDetailRequest.getTotalPrice());
            orderDetail.setColor(orderDetailRequest.getColor());
        } catch (Exception e) {
            throw new DataNotFoundException("Cannot update order detail with id: " + orderDetailId);
        }

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteOrderDetail(Long orderId) {
        orderDetailRepository.deleteById(orderId);
    }

    @Override
    public OrderDetail getOrderDetailById(Long orderId) throws DataNotFoundException {
        return orderDetailRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order detail not found with id: " + orderId));
    }

    @Override
    public List<OrderDetail> getAllOrderDetails(Long orderId) throws DataNotFoundException {
        try {
            return orderDetailRepository.findByOrderId(orderId);
        } catch (Exception e) {
            throw new DataNotFoundException("Cannot get order details");
        }
    }
}
