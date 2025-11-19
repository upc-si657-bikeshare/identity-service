package com.bikeshare.identity.auth;

import com.bikeshare.identity.profile.ProfileService;
import com.bikeshare.identity.user.User;
import com.bikeshare.identity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalStateException("El correo electrónico ya está en uso.");
        }

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .isOwner(request.isOwner())
                .build();
        User savedUser = userRepository.save(user);

        profileService.createProfileForUser(savedUser);
        return new AuthResponse("token_jwt_de_ejemplo" + savedUser.getEmail());
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña inválidos"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Usuario o contraseña inválidos");
        }

        return new AuthResponse("token_jwt_de_ejemplo" + user.getEmail());
    }
}