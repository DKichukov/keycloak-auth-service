package org.security.keycloak.service;

public interface RoleService {
    void assignRole(String userId ,String roleName);
    void deleteRole(String userId ,String roleName);
}
