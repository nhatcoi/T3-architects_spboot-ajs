package org.example.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequest {
    @NotEmpty(message = "Name is required")
    private String name;
}
