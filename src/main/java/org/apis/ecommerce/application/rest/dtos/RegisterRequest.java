package org.apis.ecommerce.application.rest.dtos;

import jakarta.validation.constraints.*;
import org.apis.ecommerce.domain.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El nombre no puede estar en blanco.")
    @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres.")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar en blanco.")
    @Size(max = 255, message = "El apellido no puede tener más de 255 caracteres.")
    private String lastName;

    @NotBlank(message = "El email no puede estar en blanco.")
    @Email(message = "El formato del email no es válido.")
    @Size(max = 255, message = "El email no puede tener más de 255 caracteres.")
    private String email;

    @NotBlank(message = "La contraseña no puede estar en blanco.")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres.")
    private String password;

    @NotNull(message = "El rol no puede ser nulo.")
    private Role role; // Asumiendo que `Role` es una enumeración o clase válida con los valores 'USER' y 'ADMIN'

    @NotNull(message = "La fecha de nacimiento no puede ser nula.")
    @Past(message = "La fecha de nacimiento debe ser en el pasado.")
    private LocalDate birthDate;

    @NotBlank(message = "El nombre de usuario no puede estar en blanco.")
    @Size(max = 255, message = "El nombre de usuario no puede tener más de 255 caracteres.")
    private String userName;
}