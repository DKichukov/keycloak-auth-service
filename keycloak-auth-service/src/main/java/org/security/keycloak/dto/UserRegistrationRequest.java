package org.security.keycloak.dto;

public record UserRegistrationRequest(String username, String password, String firstname, String lastname){

}
