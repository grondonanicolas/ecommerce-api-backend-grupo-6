package org.apis.ecommerce.application.rest.controllers.config;

import org.apis.ecommerce.domain.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors() // Activar CORS en HttpSecurity
                                .and()
                                .authorizeHttpRequests(req -> req.requestMatchers("/login", "/register").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/products").hasAuthority(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.PUT, "/products/*").hasAuthority(Role.ADMIN.name())
                                        .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedOrigin("http://localhost:5173"); // Permitir tu frontend en localhost
                configuration.addAllowedMethod("*"); // Permitir todos los métodos (GET, POST, etc.)
                configuration.addAllowedHeader("*"); // Permitir todos los encabezados
                configuration.addAllowedHeader("Authorization"); // Agrega Authorization explícitamente
                configuration.addAllowedHeader("Content-Type"); // Agrega Content-Type explícitamente si se necesita
                configuration.setAllowCredentials(true); // Permitir credenciales (tokens, cookies)
                configuration.addAllowedOriginPattern("*");
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);

                return source;
        }
}
