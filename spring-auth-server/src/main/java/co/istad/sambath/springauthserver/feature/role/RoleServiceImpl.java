package co.istad.sambath.springauthserver.feature.role;

import co.istad.sambath.springauthserver.domain.Role;
import co.istad.sambath.springauthserver.domain.RoleRepository;
import co.istad.sambath.springauthserver.domain.dto.RoleRequest;
import co.istad.sambath.springauthserver.domain.dto.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {

        if(roleRepository.existsByRole(roleRequest.role()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exists");

        Role role = Role.builder()
                .uuid(UUID.randomUUID().toString())
                .role(roleRequest.role())
                .build();

        role = roleRepository.save(role);

        return new RoleResponse(
                role.getUuid(),
                role.getRole()
        );
    }
}
