package org.apis.ecommerce.application.rest.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AddProductToCartDto {
    @NotNull(message = "El productId no puede ser nulo")
    Integer productId;

    @Min(value = 1, message = "El quantity tiene que ser mayor a 0")
    @NotNull(message = "El quantity no puede ser nulo")
    Integer quantity; 
}
