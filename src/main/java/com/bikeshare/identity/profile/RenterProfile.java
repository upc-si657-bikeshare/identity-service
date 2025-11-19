package com.bikeshare.identity.profile;

import com.bikeshare.identity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "renter_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RenterProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String paymentMethod;
    private String preferredBikeType;
    private boolean notificationsEnabled;
}