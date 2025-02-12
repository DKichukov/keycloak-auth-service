package org.security.keycloak.service;

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.security.keycloak.dto.UserRegistrationRequest;

import java.util.List;

public interface UserService {

    void createUser(UserRegistrationRequest userRegistrationRequest);

    void sendVerificationEmail(String userId);
    void deleteUser(String userId);
    void forgotPassword(String username);
    UserResource getUser(String userId);
    List<RoleRepresentation> getUserRoles(String userId);

    List<GroupRepresentation> getUserGroups(String userId);
}
