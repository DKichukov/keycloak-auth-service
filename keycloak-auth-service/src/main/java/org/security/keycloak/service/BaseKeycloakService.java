package org.security.keycloak.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseKeycloakService {

    private final Keycloak keycloak;

    @Value("${app.keycloak.realm}")
    private String real;

    public UsersResource getUsersResource() {
        return keycloak.realm(real).users();
    }

    public RolesResource getRolesResource() {
        return keycloak.realm(real).roles();
    }
}

