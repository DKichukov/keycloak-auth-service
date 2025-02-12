package org.security.keycloak.controller;

import lombok.RequiredArgsConstructor;
import org.security.keycloak.service.GroupService;
import org.security.keycloak.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PutMapping("/{groupId}/assign/users/{userId}")
    public ResponseEntity<?> assignUserToAGroup(@PathVariable String userId, @PathVariable String groupId) {

        groupService.assignGroup(userId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/{groupId}/remove/users/{userId}")
    public ResponseEntity<?> unAssignToAGroup(@PathVariable String userId, @PathVariable String groupId) {

        groupService.deleteGroupFromUser(userId, groupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
