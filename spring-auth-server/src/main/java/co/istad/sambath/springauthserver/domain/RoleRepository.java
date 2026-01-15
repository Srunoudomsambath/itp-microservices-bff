package co.istad.sambath.springauthserver.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(String role);

    boolean existsByRole(String role);
}
