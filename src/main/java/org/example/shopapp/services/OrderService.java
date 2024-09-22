package org.example.shopapp.services;

import lombok.RequiredArgsConstructor;
import org.example.shopapp.common.OrderStatus;
import org.example.shopapp.common.PaymentMethod;
import org.example.shopapp.dtos.OrderRequest;
import org.example.shopapp.entities.Order;
import org.example.shopapp.entities.User;
import org.example.shopapp.exceptions.DataNotFoundException;
import org.example.shopapp.reposistories.OrderRepository;
import org.example.shopapp.reposistories.UserRepository;
import org.example.shopapp.services.serviceImpl.OrderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceImpl {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderRequest orderRequest) {
        // check if user exists
        User user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        assert user != null;

        // map request to entity
        Order order = modelMapper.map(orderRequest, Order.class);

        // handle logic
        LocalDateTime now = LocalDateTime.now();
        order.setOrderDate(now);
        order.setStatus(OrderStatus.PENDING.toString());
        order.setShippingDate(now.plusDays(1));
        order.setTrackingNumber(UUID.randomUUID().toString());
        order.setPaymentDate(orderRequest.getPaymentMethod().equals(PaymentMethod.BANKING.getText()) ? now : null);
        order.setActive(true);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, OrderRequest orderRequest) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()) {
            Order orderToUpdate = order.get();

            orderToUpdate.setFullName(orderRequest.getFullName());
            orderToUpdate.setEmail(orderRequest.getEmail());
            orderToUpdate.setPhoneNumber(orderRequest.getPhoneNumber());
            orderToUpdate.setAddress(orderRequest.getAddress());
            orderToUpdate.setNote(orderRequest.getNote());
            orderToUpdate.setTotalPrice(orderRequest.getTotalPrice());
            orderToUpdate.setShippingMethod(orderRequest.getShippingMethod());
            orderToUpdate.setShippingAddress(orderRequest.getShippingAddress());
            orderToUpdate.setPaymentMethod(orderRequest.getPaymentMethod());

            return orderRepository.save(orderToUpdate);
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order getOrderById(Long id) throws DataNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
    }
}
