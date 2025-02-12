package org.security.keycloak.service;

public interface GroupService {
    void assignGroup(String userId ,String groupId);
    void deleteGroupFromUser(String userId ,String groupId);

}
