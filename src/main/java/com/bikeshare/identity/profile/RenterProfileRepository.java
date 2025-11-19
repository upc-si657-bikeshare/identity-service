package com.bikeshare.identity.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RenterProfileRepository extends JpaRepository<RenterProfile, Long> {
    Optional<RenterProfile> findByUserId(Long userId);
}