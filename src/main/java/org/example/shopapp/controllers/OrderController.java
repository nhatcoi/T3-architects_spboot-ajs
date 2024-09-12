package org.example.shopapp.controllers;


import jakarta.validation.Valid;
import org.example.shopapp.dtos.OrderDTO;
import org.example.shopapp.dtos.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> insertOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errors = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors.toString());
            }
            return ResponseEntity.ok("Insert order successfully: " + orderDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // List all orders of a user
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        try {
            //
            return ResponseEntity.ok("Get all orders of user: " + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update info pf an order -- job of admin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") Long id, @RequestBody OrderDTO orderDTO) {
        try {
            return ResponseEntity.ok("Update order successfully: " + id + " " + orderDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete an order -- soft erase
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") Long orderId) {
        try {
            return ResponseEntity.ok("Delete order successfully: " + orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
