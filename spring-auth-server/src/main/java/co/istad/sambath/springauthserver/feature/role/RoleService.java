package co.istad.sambath.springauthserver.feature.role;

import co.istad.sambath.springauthserver.domain.dto.RoleRequest;
import co.istad.sambath.springauthserver.domain.dto.RoleResponse;

public interface RoleService {

    RoleResponse createRole(RoleRequest roleRequest);
}
