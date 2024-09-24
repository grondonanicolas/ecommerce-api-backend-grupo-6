package org.apis.ecommerce.application.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryCreateDTO {
    @NotBlank(message = "El nombre de la categoria no puede estar vacio.")
    String category;
}
