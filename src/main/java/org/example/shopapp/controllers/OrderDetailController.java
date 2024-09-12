package org.example.shopapp.controllers;


import jakarta.validation.Valid;
import org.example.shopapp.dtos.OrderDetailDTO;
import org.example.shopapp.dtos.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order-details")
public class OrderDetailController {

    // tạo mới 1 order detail
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok("Create order detail: " + orderDetailDTO);
    }

    // lấy thông tin chi tiết của 1 order detail
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok("Get order detail: " + id);
    }

    // lấy danh sách các order details của 1 order
    @GetMapping("order/{order_id}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("order_id") Long orderId) {
        return ResponseEntity.ok("Get all order details of order: " + orderId);
    }

    // cập nhật thông tin của 1 order detail
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id, @RequestBody OrderDetailDTO newOrderDetailDTO) {
        return ResponseEntity.ok("Update order detail: " + id + "---" + newOrderDetailDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok("Delete order detail: " + id);
    }
}
