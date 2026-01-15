package co.istad.sambath.springauthserver.feature.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfoResponse {
    private String uuid;
    private String username;
    private String email;
    private String givenName;
    private String familyName;
    private List<String> roles;
}
