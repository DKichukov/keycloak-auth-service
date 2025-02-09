package org.security.keycloak.service;

import org.security.keycloak.dto.NewUserRecord;

public interface UserService {

    void createUser(NewUserRecord newUserRecord);

}
