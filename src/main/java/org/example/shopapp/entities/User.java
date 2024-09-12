package org.example.shopapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_id")
    private int facebookId;

    @Column(name = "google_id")
    private int googleId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
