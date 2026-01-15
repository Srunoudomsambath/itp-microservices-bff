package co.istad.sambath.springauthserver.init;

import co.istad.sambath.springauthserver.domain.Role;
import co.istad.sambath.springauthserver.domain.RoleRepository;
import co.istad.sambath.springauthserver.domain.User;
import co.istad.sambath.springauthserver.feature.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class InitRoleAndUser {

    private final RoleRepository repo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Role role = Role.builder()
                .uuid(UUID.randomUUID().toString())
                .role("USER")
                .build();

        User user = User.builder()
                .uuid(UUID.randomUUID().toString())
                .roles(List.of(role))
                .email("Oudomsambath@gmail.com")
                .password(passwordEncoder.encode("password"))
                .username("sambath")
                .familyName("Oudom")
                .givenName("Sambath")
                .isEnabled(true)
                .build();
        repo.save(role);
        userRepo.save(user);
    }
}
