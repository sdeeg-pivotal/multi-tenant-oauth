package com.vmware.multitenantresourceserver.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;

public class InvalidBearerTokenException extends OAuth2AuthenticationException {

    /**
     * Construct an instance of {@link InvalidBearerTokenException} given the provided
     * description.
     *
     * The description will be wrapped into an {@link org.springframework.security.oauth2.core.OAuth2Error}
     * instance as the {@code error_description}.
     *
     * @param description the description
     */
    public InvalidBearerTokenException(String description) {
        super(invalidToken(description));
    }

    /**
     * Construct an instance of {@link InvalidBearerTokenException} given the provided
     * description and cause
     *
     * The description will be wrapped into an {@link org.springframework.security.oauth2.core.OAuth2Error}
     * instance as the {@code error_description}.
     *
     * @param description the description
     * @param cause the causing exception
     */
    public InvalidBearerTokenException(String description, Throwable cause) {
        super(invalidToken(description), cause);
    }

    private static OAuth2Error invalidToken(String description) {
        OAuth2Error error = new BearerTokenError(BearerTokenErrorCodes.INVALID_TOKEN, HttpStatus.BAD_REQUEST,
                description, "https://tools.ietf.org/html/rfc6750#section-3.1");
        return error;
    }
}