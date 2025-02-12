package org.security.keycloak.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.security.keycloak.service.BaseKeycloakService;
import org.security.keycloak.service.RoleService;
import org.security.keycloak.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final BaseKeycloakService keycloakService;

    private final UserService userService;

    @Override
    public void assignRole(String userId, String roleName) {

        UserResource user = userService.getUser(userId);
        RolesResource rolesResource = keycloakService.getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        user.roles().realmLevel().add(Collections.singletonList(representation));
    }

    @Override
    public void deleteRole(String userId, String roleName) {
        UserResource user = userService.getUser(userId);
        RolesResource rolesResource = keycloakService.getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        user.roles().realmLevel().remove(Collections.singletonList(representation));
    }

}
