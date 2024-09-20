package org.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @Min(value = 0, message = "Price must be greater than 0")
    @Max(value = 1000000000, message = "Price must be less than 1000000000")
    private Float price;
    private String thumbnail;
    private String description;

    // mapping the category_id field from the request body to categoryId field in the ProductDTO class
    @JsonProperty("category_id")
    private long categoryId;

//    private MultipartFile file;
//    private List<MultipartFile> files;
}
