package org.apis.ecommerce.application.rest.controllers;

import org.apis.ecommerce.domain.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apis.ecommerce.application.rest.dtos.AuthenticationRequest;
import org.apis.ecommerce.application.rest.dtos.AuthenticationResponse;
import org.apis.ecommerce.application.rest.dtos.RegisterRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
            AuthenticationResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>("El nombre de usuario ya está en uso. Por favor, elige otro.", HttpStatus.CONFLICT);
    }
}