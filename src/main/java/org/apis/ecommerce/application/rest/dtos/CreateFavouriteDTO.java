package org.apis.ecommerce.application.rest.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor 
public class CreateFavouriteDTO {

    @NotNull(message = "ProductId no puede ser null")
    @Min(value=1, message = "El productId debe ser mayor a 0")
    private Integer productId;
}
