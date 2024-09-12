package org.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("image_url")
    private String imageUrl;
}
