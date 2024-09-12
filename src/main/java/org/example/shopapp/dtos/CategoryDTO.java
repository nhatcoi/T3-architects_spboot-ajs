package org.example.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.example.shopapp.entities.Category;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {
    @NotEmpty(message = "Name is required")
    private String name;
}
