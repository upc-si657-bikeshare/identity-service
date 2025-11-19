package com.bikeshare.identity.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@Tag(name = "Profiles", description = "Gestión de perfiles de usuario")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "Obtener perfil (owner o renter) por ID de usuario")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }

    @Operation(summary = "Actualizar perfil de un arrendatario (Renter)")
    @PutMapping("/renter/{userId}")
    public ResponseEntity<ProfileResponse> updateRenterProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateRenterProfileRequest request
    ) {
        return ResponseEntity.ok(profileService.updateRenterProfile(userId, request));
    }

    @Operation(summary = "Actualizar perfil de un propietario (Owner)")
    @PutMapping("/owner/{userId}")
    public ResponseEntity<ProfileResponse> updateOwnerProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateOwnerProfileRequest request
    ) {
        return ResponseEntity.ok(profileService.updateOwnerProfile(userId, request));
    }
}