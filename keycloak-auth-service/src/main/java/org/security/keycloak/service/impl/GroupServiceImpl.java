package org.security.keycloak.service.impl;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UserResource;
import org.security.keycloak.service.BaseKeycloakService;
import org.security.keycloak.service.GroupService;
import org.security.keycloak.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final BaseKeycloakService keycloakService;

    private final UserService userService;

    @Override
    public void assignGroup(String userId, String groupId) {

        UserResource user = userService.getUser(userId);
        user.joinGroup(groupId);
    }

    @Override
    public void deleteGroupFromUser(String userId, String groupId) {

        UserResource user = userService.getUser(userId);
        user.leaveGroup(groupId);
    }

}
