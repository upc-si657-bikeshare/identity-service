package com.bikeshare.identity.profile;

public record UpdateRenterProfileRequest(
        String fullName,
        String phone,
        String address,
        String avatarUrl,
        String paymentMethod,
        String preferredBikeType,
        Boolean notificationsEnabled
) {}