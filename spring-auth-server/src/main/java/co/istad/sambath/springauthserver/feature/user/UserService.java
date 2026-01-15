package co.istad.sambath.springauthserver.feature.user;


import co.istad.sambath.springauthserver.feature.user.dto.UserReqeust;
import co.istad.sambath.springauthserver.feature.user.dto.UserResponse;

public interface UserService {

    UserResponse createUser(UserReqeust userRequest);
    void disableUser(String uuid);
    void enableUser(String uuid);

}
