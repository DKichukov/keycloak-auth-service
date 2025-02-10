package org.security.keycloak.service.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.security.keycloak.dto.UserRegistrationRequest;
import org.security.keycloak.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Override
    public void createUser(UserRegistrationRequest userRegistrationRequest) {

        UserRepresentation userRepresentation = getUserRepresentation(userRegistrationRequest);

        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(userRepresentation);

        log.info("Status Code "+response.getStatus());

        if(!Objects.equals(201,response.getStatus())){

            throw new RuntimeException("Status code "+response.getStatus());
        }

        log.info("New user has bee created");


        if(!Objects.equals(201,response.getStatus())){
            throw new RuntimeException("Status code " + response.getStatus());
        }
        log.info("New user has bee created");

        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(userRegistrationRequest.username(), true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        sendVerificationEmail(userRepresentation1.getId());



    }

    @Override
    public void sendVerificationEmail(String userId) {

        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();

    }

    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);
    }


    private static UserRepresentation getUserRepresentation(UserRegistrationRequest userRegistrationRequest) {
        UserRepresentation  userRepresentation= new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(userRegistrationRequest.firstname());
        userRepresentation.setLastName(userRegistrationRequest.lastname());
        userRepresentation.setUsername(userRegistrationRequest.username());
        userRepresentation.setEmail(userRegistrationRequest.username());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistrationRequest.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));
        return userRepresentation;
    }

    private UsersResource getUsersResource(){

        return keycloak.realm(realm).users();
    }


}
