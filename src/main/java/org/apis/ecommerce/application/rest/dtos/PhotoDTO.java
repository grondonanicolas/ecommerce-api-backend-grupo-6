package org.apis.ecommerce.application.rest.dtos;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PhotoDTO {

    @NotNull(message = "La prioridad no puede ser nula")
    @Min(value = 0, message = "El stock debe ser mayor o igual que 0")
    private int priority;

    @NotNull(message = "La url de la imagen no puede estar vacia")
    private String url;
}