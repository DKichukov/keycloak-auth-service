package org.security.keycloak.service;

import org.security.keycloak.dto.UserRegistrationRequest;

public interface UserService {

    void createUser(UserRegistrationRequest userRegistrationRequest);

    void sendVerificationEmail(String userId);
    void deleteUser(String userId);
}
