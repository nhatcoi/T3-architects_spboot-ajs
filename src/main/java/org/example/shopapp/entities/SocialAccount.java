package org.example.shopapp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "social_accounts")
@Entity
@Builder
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider", nullable = false, length = 50)
    private String provider;

    @Column(name = "provider_id", nullable = false, length = 50)
    private int providerId;

    @Column(name = "email", nullable = false)
    private String email;
}
