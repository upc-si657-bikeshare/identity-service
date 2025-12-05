package com.bikeshare.identity.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ForceResetRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String newPassword
) {}