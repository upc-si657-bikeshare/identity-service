package com.bikeshare.identity.profile;

import com.bikeshare.identity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "owner_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OwnerProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean isVerified;
    private String payoutEmail;
    private String bankAccountNumber;
    private String yapePhoneNumber;
}