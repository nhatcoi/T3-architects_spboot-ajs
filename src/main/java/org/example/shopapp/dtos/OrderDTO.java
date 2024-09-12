package org.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1, message = "User ID must be greater than 0")
    private Long userId;

    @JsonProperty("full_name")
    private String fullName;

    @Email(message = "Email is not valid")
    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(min = 8, max = 15, message = "Phone number must be 8 characters")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("total_price")
    @Min(value = 0, message = "Total price must be >= 0")
    private Float totalPrice;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;
}
