package org.example.shopapp.dtos.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
public class OrderDetailResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("product_name")
    private Float price;
    @JsonProperty("number_of_products")
    private Integer numberOfProducts;
    @JsonProperty("total_price")
    private Float totalPrice;
    @JsonProperty("color")
    private String color;
}
