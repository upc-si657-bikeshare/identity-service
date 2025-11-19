package com.bikeshare.identity.profile;

import com.bikeshare.identity.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String avatarUrl;
    private boolean isOwner;
    private String publicBio;
    private Boolean isVerified;
    private String payoutEmail;
    private String bankAccountNumber;
    private String yapePhoneNumber;
    private String paymentMethod;
    private String preferredBikeType;
    private Boolean notificationsEnabled;

    public static ProfileResponse from(User user, OwnerProfile profile) {
        ProfileResponse dto = from(user);
        dto.isVerified = profile.isVerified();
        dto.payoutEmail = profile.getPayoutEmail();
        dto.bankAccountNumber = profile.getBankAccountNumber();
        dto.yapePhoneNumber = profile.getYapePhoneNumber();
        return dto;
    }

    public static ProfileResponse from(User user, RenterProfile profile) {
        ProfileResponse dto = from(user);
        dto.paymentMethod = profile.getPaymentMethod();
        dto.preferredBikeType = profile.getPreferredBikeType();
        dto.notificationsEnabled = profile.isNotificationsEnabled();
        return dto;
    }

    private static ProfileResponse from(User user) {
        ProfileResponse dto = new ProfileResponse();
        dto.id = user.getId();
        dto.fullName = user.getFullName();
        dto.email = user.getEmail();
        dto.phone = user.getPhone();
        dto.address = user.getAddress();
        dto.avatarUrl = user.getAvatarUrl();
        dto.isOwner = user.isOwner();
        dto.publicBio = user.getPublicBio();
        return dto;
    }
}