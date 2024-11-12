package org.apis.ecommerce.application.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String email;

    private String username;

    @NotBlank(message = "La contraseña no puede estar en blanco.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String password;
}
