package org.example.shopapp.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopapp.dtos.OrderDetailRequest;
import org.example.shopapp.dtos.responses.OrderDetailResponse;
import org.example.shopapp.entities.OrderDetail;
import org.example.shopapp.exceptions.DataNotFoundException;
import org.example.shopapp.services.serviceImpl.OrderDetailServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order-details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailServiceImpl orderDetailService;
    private final ModelMapper modelMapper;

    // tạo mới 1 order detail

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailRequest orderDetailRequest) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailRequest);
        OrderDetailResponse orderDetailResponse = modelMapper.map(orderDetail, OrderDetailResponse.class);
        return ResponseEntity.ok().body(orderDetailResponse);
    }

    // lấy thông tin chi tiết của 1 order detail
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long orderId) throws DataNotFoundException {
        OrderDetailResponse orderDetailResponse = modelMapper.map(orderDetailService.getOrderDetailById(orderId), OrderDetailResponse.class);
        return ResponseEntity.ok().body(orderDetailResponse);
    }

    // lấy danh sách các order details của 1 order
    @GetMapping("order/{order_id}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("order_id") Long orderId) throws DataNotFoundException {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailResponse.class))
                .toList();
        return ResponseEntity.ok().body(orderDetailResponses);
    }

    // cập nhật thông tin của 1 order detail
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long orderDetailId, @RequestBody OrderDetailRequest orderDetailRequest) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.updateOrderDetail(orderDetailId, orderDetailRequest);
        OrderDetailResponse orderDetailResponse = modelMapper.map(orderDetail, OrderDetailResponse.class);
        return ResponseEntity.ok().body(orderDetailResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok("Delete order detail: " + id);
    }
}
