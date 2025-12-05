package com.bikeshare.identity.auth;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        Long userId,
        String fullName,
        boolean isOwner
) {}