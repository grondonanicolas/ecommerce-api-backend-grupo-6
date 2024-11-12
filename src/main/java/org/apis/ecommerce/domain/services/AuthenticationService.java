package org.apis.ecommerce.domain.services;


import lombok.RequiredArgsConstructor;
import org.apis.ecommerce.application.rest.controllers.config.JwtService;
import org.apis.ecommerce.application.rest.dtos.AuthenticationRequest;
import org.apis.ecommerce.application.rest.dtos.AuthenticationResponse;
import org.apis.ecommerce.application.rest.dtos.RegisterRequest;
import org.apis.ecommerce.application.rest.services.IAuthenticationService;
import org.apis.ecommerce.infrastructure.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.apis.ecommerce.domain.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
        private final UserRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final CartService cartService;

        public AuthenticationResponse register(RegisterRequest request) {
                var user = User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .birthDate(request.getBirthDate())
                        .username(request.getUserName())
                        .historic(new ArrayList<>())
                        .favourite(new ArrayList<>())
                        .imageURL(request.getImage())
                        .build();
                repository.save(user);
                cartService.createUserCart(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                User user = repository.findByEmail(request.getEmail()).orElseGet(() ->
                        repository.findByUsername(request.getUsername()).orElseThrow()
                );

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                user.getEmail(),
                                                request.getPassword()));

                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
        }
}
