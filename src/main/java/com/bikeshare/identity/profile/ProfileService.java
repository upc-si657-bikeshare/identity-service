package com.bikeshare.identity.profile;

import com.bikeshare.identity.user.User;
import com.bikeshare.identity.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final OwnerProfileRepository ownerProfileRepo;
    private final RenterProfileRepository renterProfileRepo;
    private final UserRepository userRepo;

    /**
     * Crea automáticamente el perfil según el rol del usuario.
     * Se usa, por ejemplo, justo después de registrar el usuario.
     */
    @Transactional
    public void createProfileForUser(User user) {
        if (user.isOwner()) {
            ownerProfileRepo.findByUserId(user.getId())
                    .orElseGet(() -> ownerProfileRepo.save(
                            OwnerProfile.builder()
                                    .user(user)
                                    .isVerified(false)
                                    .build()
                    ));
        } else {
            renterProfileRepo.findByUserId(user.getId())
                    .orElseGet(() -> renterProfileRepo.save(
                            RenterProfile.builder()
                                    .user(user)
                                    .notificationsEnabled(true)
                                    .build()
                    ));
        }
    }

    /**
     * Devuelve un response unificado del perfil (owner o renter) según el rol del usuario.
     */
    @Transactional
    public ProfileResponse getProfileByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (user.isOwner()) {
            OwnerProfile profile = ownerProfileRepo.findByUserId(userId)
                    .orElseThrow(() -> new IllegalStateException("Perfil de propietario no encontrado"));
            return ProfileResponse.from(user, profile);
        } else {
            RenterProfile profile = renterProfileRepo.findByUserId(userId)
                    .orElseThrow(() -> new IllegalStateException("Perfil de arrendatario no encontrado"));
            return ProfileResponse.from(user, profile);
        }
    }

    /**
     * Actualiza parcialmente el perfil de Renter y los datos básicos del usuario.
     */
    @Transactional
    public ProfileResponse updateRenterProfile(Long userId, UpdateRenterProfileRequest request) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (user.isOwner()) {
            throw new IllegalStateException("El usuario es Owner; use el endpoint de Owner.");
        }

        RenterProfile profile = renterProfileRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Perfil de arrendatario no encontrado"));

        if (request.fullName() != null && !request.fullName().isBlank()) user.setFullName(request.fullName());
        if (request.phone() != null && !request.phone().isBlank()) user.setPhone(request.phone());
        if (request.address() != null && !request.address().isBlank()) user.setAddress(request.address());
        if (request.avatarUrl() != null && !request.avatarUrl().isBlank()) user.setAvatarUrl(request.avatarUrl());
        if (request.paymentMethod() != null) profile.setPaymentMethod(request.paymentMethod());
        if (request.preferredBikeType() != null) profile.setPreferredBikeType(request.preferredBikeType());
        if (request.notificationsEnabled() != null) profile.setNotificationsEnabled(request.notificationsEnabled());

        userRepo.save(user);
        renterProfileRepo.save(profile);

        return ProfileResponse.from(user, profile);
    }

    /**
     * Actualiza parcialmente el perfil de Owner y los datos básicos del usuario.
     */
    @Transactional
    public ProfileResponse updateOwnerProfile(Long userId, UpdateOwnerProfileRequest request) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (!user.isOwner()) {
            throw new IllegalStateException("El usuario es Renter; use el endpoint de Renter.");
        }

        OwnerProfile profile = ownerProfileRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Perfil de propietario no encontrado"));

        if (request.fullName() != null && !request.fullName().isBlank()) user.setFullName(request.fullName());
        if (request.phone() != null && !request.phone().isBlank()) user.setPhone(request.phone());
        if (request.publicBio() != null) user.setPublicBio(request.publicBio());
        if (request.avatarUrl() != null && !request.avatarUrl().isBlank()) user.setAvatarUrl(request.avatarUrl());
        if (request.payoutEmail() != null) profile.setPayoutEmail(request.payoutEmail());
        if (request.bankAccountNumber() != null) profile.setBankAccountNumber(request.bankAccountNumber());
        if (request.yapePhoneNumber() != null) profile.setYapePhoneNumber(request.yapePhoneNumber());

        userRepo.save(user);
        ownerProfileRepo.save(profile);

        return ProfileResponse.from(user, profile);
    }
}