package io.hotdogger.login.security;

/**
 * AuthResponse is the response that holds a JWT and is given to the client/user when logging in or
 * authenticating the user is successful.
 */
public class AuthResponse {

    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    //a getter only because the field/member variable is private
    public String getJwt() {
        return jwt;
    }
}