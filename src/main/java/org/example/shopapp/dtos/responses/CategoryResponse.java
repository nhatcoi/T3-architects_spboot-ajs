package org.example.shopapp.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryResponse {
    private Long id;
    private String name;
}
