package org.apis.ecommerce.application.rest.controllers.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales incorrectas.");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return new ResponseEntity<>("Error inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}