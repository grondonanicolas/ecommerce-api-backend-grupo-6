package org.apis.ecommerce.application.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserProfileDTO {
    private String nombre;
    private String apellido;
    private String email;
}
