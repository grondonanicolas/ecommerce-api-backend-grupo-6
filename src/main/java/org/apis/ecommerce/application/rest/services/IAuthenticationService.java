package org.apis.ecommerce.application.rest.services;

import org.apis.ecommerce.application.rest.dtos.AuthenticationRequest;
import org.apis.ecommerce.application.rest.dtos.AuthenticationResponse;
import org.apis.ecommerce.application.rest.dtos.RegisterRequest;

public interface IAuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
}
