package org.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {

    @JsonProperty("order_id")
    @Min(value = 1, message = "Order ID must be greater than 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product ID must be greater than 0")
    private Long productId;

    @Min(value = 0, message = "Quantity must be >= 0")
    private Float price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "Number of products must be greater than 0")
    private int numberOfProducts;

    @JsonProperty("total_price")
    @Min(value = 0, message = "Total price must be >= 0")
    private Float totalPrice;

    @JsonProperty("color")
    private String color;
}
