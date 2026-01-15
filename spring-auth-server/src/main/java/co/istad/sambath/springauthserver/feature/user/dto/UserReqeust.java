package co.istad.sambath.springauthserver.feature.user.dto;

import java.util.List;

public record UserReqeust (
        String username,
        String email,
        String password,
        String givenName,
        String familyName,
        List<String> roles
){
}
