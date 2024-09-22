package org.example.shopapp.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.OrderRequest;
import org.example.shopapp.dtos.responses.OrderResponse;
import org.example.shopapp.entities.Order;
import org.example.shopapp.services.serviceImpl.OrderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> insertOrder(@Valid @RequestBody OrderRequest orderRequest, BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errors = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }

            // create order
            Order order = orderService.createOrder(orderRequest);

            // entity to response
            OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
            return ResponseEntity.ok().body(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // List all orders of a user
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        try {
            OrderResponse orderResponse = modelMapper
                    .map(orderService.getOrderById(userId), OrderResponse.class);
            return ResponseEntity.ok().body(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update info pf an order -- job of admin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") Long orderId, @RequestBody OrderRequest orderRequest) {
        try {
            Order orderUpdated = orderService.updateOrder(orderId, orderRequest);
            OrderResponse orderResponse = modelMapper.map(orderUpdated, OrderResponse.class);
            return ResponseEntity.ok().body(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete an order -- soft erase
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") Long orderId) {
        try {
            OrderResponse orderResponse = modelMapper
                    .map(orderService.getOrderById(orderId), OrderResponse.class);
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("Deleted order: " + orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
