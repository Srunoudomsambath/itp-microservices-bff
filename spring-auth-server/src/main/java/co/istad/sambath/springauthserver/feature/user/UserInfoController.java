package co.istad.sambath.springauthserver.feature.user;

import co.istad.sambath.springauthserver.domain.User;
import co.istad.sambath.springauthserver.feature.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication; // FIXED: Wrong import
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public UserInfoResponse getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        // Get username from JWT token
        String username;
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            username = jwt.getClaimAsString("username");
        } else {
            username = authentication.getName();
        }

        // Fetch user from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Build and return response using UserInfoResponse DTO
        return UserInfoResponse.builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .email(user.getEmail())
                .givenName(user.getGivenName())
                .familyName(user.getFamilyName())
                .roles(user.getRoles().stream()
                        .map(role -> role.getRole())
                        .collect(Collectors.toList()))
                .build();
    }
}